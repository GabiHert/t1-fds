package com.fds.siscaa.useCases.useCases;

import java.time.LocalDate;
import java.util.Date;
/*
 * O sistema deverá ser o responsável por todas as operações de manutenção dos cadastros
(clientes, aplicativos e assinaturas) bem como pelas operações relativas à cobrança, tais como,
atualizar o preço das assinaturas, atualizar a data de validade etc.



No plano básico cada mensalidade paga estende a assinatura por mais 30 dias.
A extensão do prazo se dá somando-se mais 30 dias ao prazo de validade atual ou mais 30 dias a partir da data de pagamento no caso de uma assinatura “cancelada” 
(com prazo de validade anterior a data corrente). Isso significa que para reativar uma assinatura basta pagar uma mensalidade.

Cada pagamento deve corresponder a uma mensalidade por vez. Se o valor do pagamento
estiver correto, deve-se retornar a nova data de validade e o valor a ser estornado é zero. Se o
valor estiver incorreto ou se a promoção informada for inexistente ou não for aplicável, a data
de validade retornada deve ser a mesma que já estava registrada e o valor pago deve ser
retornado como sendo o valor que será estornado (devolvido).

A sistema permite ainda promoções que estendem o período dos pagamentos, como por
exemplo pagamento anual com desconto de 40%, promoção pague 30 e ganhe 45 dias (estas
promoções e novas podem ser disponibilizadas a critério da empresa).

O sistema deve manter, também, o registro de todos os pagamentos efetuados. Sempre que o
sistema receber uma notificação de pagamento, o mesmo deve ser armazenado e a assinatura
atualizada conforme o caso.

Note que as promoções podem ser tanto relativas ao prazo ganho a cada pagamento (tipo pague
uma e ganhe 45 dias etc) ou quanto ao valor pago pela mensalidade (descontos etc).
*/
import java.util.Optional;

import com.fds.siscaa.domain.entity.PaymentEntity;
import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.enums.PaymentStatus;
import com.fds.siscaa.domain.rules.PaymentRules;
import com.fds.siscaa.domain.rules.PromotionRules;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;

import lombok.AllArgsConstructor;

@AllArgsConstructor()
public class CreatePaymentUseCase {
        private final SubscriptionRepositoryAdapter subscriptionRepository;

        public CreatePaymentResponse create(LocalDate paymentDate, int subscriptionCode, float receivedAmount) {
                System.out.println(
                                "CreatePaymentUseCase - STARTED - paymentDate: " + paymentDate + " subscriptionCode: "
                                                + subscriptionCode + " valorPago: " + receivedAmount);

                /*
                 * verificar o valor do pagament
                 * se estiver correto retornar a nova date de validade
                 * se estiver incorreto ou se a promoção não for válida a data retornada deve
                 * ser que já estava registrada e o valor pago deve ser retornado como sendo o
                 * valor que será estornado
                 * 
                 * verificar se a promoção é válida
                 * 
                 */

                // get subscription by subscriptionCode
                SubscriptionEntity subscriptionEntity = subscriptionRepository
                                .getSubscriptionEntityByCode(subscriptionCode);

                // validar se da pra pagar assinatura (valores cheios) -> retorna quantos dias
                // devem ser somados a assinatura
                // verifica se existe promoção válida
                // se não existir promoção válida retorna a data de validade atual e o valor
                float monthlyFee = subscriptionEntity.getApplication().getMonthlyFee();

                int monthsToExtend = PaymentRules.getMonthsToExtendSubscription(
                                monthlyFee,
                                receivedAmount);
                int daysToExtend = monthsToExtend * 30;

                PaymentStatus paymentStatus = PaymentRules.getPaymentStatus(monthlyFee, receivedAmount);

                if (paymentStatus.equals(PaymentStatus.VALOR_INCORRETO)) {
                        CreatePaymentResponse createPaymentResultDto = new CreatePaymentResponse(
                                        paymentStatus,
                                        subscriptionEntity.getEndDate(),
                                        receivedAmount);
                        return createPaymentResultDto;
                }

                Optional<PromotionEntity> validPromotion = PromotionRules.getValidPromotion(monthsToExtend,
                                subscriptionEntity.getPromotions());

                if (validPromotion.isPresent()) {
                        monthsToExtend = PromotionRules.applyExtraDaysPromotion(monthsToExtend,
                                        validPromotion.get().getExtraDays());

                        monthlyFee = PromotionRules.applyFeePromotion(monthlyFee,
                                        validPromotion.get().getDiscountPercentage());
                }

                float refundValue = PaymentRules.getRefundValue(monthlyFee, receivedAmount);
                subscriptionRepository.updateSubscriptionStartDateAndEndDateByCode(
                                new LocalDate(),
                                new LocalDate().plusDays(daysToExtend), subscriptionCode);
                CreatePaymentResponse createPaymentResultDto = new CreatePaymentResponse(
                                paymentStatus,
                                subscriptionEntity.getEndDate(),
                                refundValue);
                return createPaymentResultDto;
        }
}
