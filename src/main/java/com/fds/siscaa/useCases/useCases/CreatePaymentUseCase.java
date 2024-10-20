package com.fds.siscaa.useCases.useCases;

import java.time.LocalDate;
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

import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;

import lombok.AllArgsConstructor;

@AllArgsConstructor()
public class CreatePaymentUseCase {
    private final SubscriptionRepositoryAdapter subscriptionRepository;

    public CreatePaymentResponse create(LocalDate paymentDate, int subscriptionCode, double valorPago) {
        System.out.println("CreatePaymentUseCase - STARTED - paymentDate: " + paymentDate + " subscriptionCode: "
                + subscriptionCode + " valorPago: " + valorPago);

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
        SubscriptionEntity subscriptionEntity = subscriptionRepository.getSubscriptionEntityByCode(subscriptionCode);

        // validar se da pra pagar assinatura (valores cheios) -> retorna quantos dias
        // devem ser somados a assinatura
        // se não for válido verifica se existe promoção válida
        // se não existir promoção válida retorna a data de validade atual e o valor

        CreatePaymentResponse createPaymentResultDto = new CreatePaymentResponse();
        return createPaymentResultDto;
    }
}
