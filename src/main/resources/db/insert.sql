-- Inserindo 10 clientes
INSERT INTO Client (code, name, email)
VALUES (1, 'João Silva', 'joao.silva@example.com');

INSERT INTO Client (code, name, email)
VALUES (2, 'Maria Souza', 'maria.souza@example.com');

INSERT INTO Client (code, name, email)
VALUES (3, 'Carlos Pereira', 'carlos.pereira@example.com');

INSERT INTO Client (code, name, email)
VALUES (4, 'Ana Oliveira', 'ana.oliveira@example.com');

INSERT INTO Client (code, name, email)
VALUES (5, 'Pedro Santos', 'pedro.santos@example.com');

INSERT INTO Client (code, name, email)
VALUES (6, 'Julia Costa', 'julia.costa@example.com');

INSERT INTO Client (code, name, email)
VALUES (7, 'Rafael Lima', 'rafael.lima@example.com');

INSERT INTO Client (code, name, email)
VALUES (8, 'Mariana Barbosa', 'mariana.barbosa@example.com');

INSERT INTO Client (code, name, email)
VALUES (9, 'Lucas Alves', 'lucas.alves@example.com');

INSERT INTO Client (code, name, email)
VALUES (10, 'Fernanda Rocha', 'fernanda.rocha@example.com');

-- Inserindo 5 aplicativos
INSERT INTO Application (code, name, monthlyFee)
VALUES (1, 'App Gestão Financeira', 29.99);

INSERT INTO Application (code, name, monthlyFee)
VALUES (2, 'App Monitoramento de Saúde', 49.99);

INSERT INTO Application (code, name, monthlyFee)
VALUES (3, 'App Treinamento Físico', 19.99);

INSERT INTO Application (code, name, monthlyFee)
VALUES (4, 'App Controle de Tarefas', 14.99);

INSERT INTO Application (code, name, monthlyFee)
VALUES (5, 'App Nutrição', 24.99);

-- Inserindo promoções com base nos exemplos fornecidos
-- Promoção 1: Pagamento anual com 40% de desconto
INSERT INTO Promotion (code, discountPercentage, extraDays, monthsRequired)
VALUES (1, 40.0, 0, 12);

-- Promoção 2: Pague 30 dias e ganhe 45 dias (15 dias extras)
INSERT INTO Promotion (code, discountPercentage, extraDays, monthsRequired)
VALUES (2, 0.0, 15, 1);

-- Assinaturas ativas no dia 15 de outubro de 2024
-- Vamos considerar que cada assinatura tem 1 mês de validade a partir da data de início

-- Assinatura 1 (começa em 10 de outubro de 2024, válida até 10 de novembro de 2024)
INSERT INTO Subscription (code, startDate, endDate, client_Code, application_Code)
VALUES (1, '2024-10-10', '2024-11-10', 1, 1);

-- Assinatura 2 (começa em 12 de outubro de 2024, válida até 12 de novembro de 2024)
INSERT INTO Subscription (code, startDate, endDate, client_Code, application_Code)
VALUES (2, '2024-10-12', '2024-11-12', 2, 2);

-- Assinatura 3 (começa hoje, 15 de outubro de 2024, válida até 15 de novembro de 2024)
INSERT INTO Subscription (code, startDate, endDate, client_Code, application_Code)
VALUES (3, '2024-10-15', '2024-11-15', 3, 3);

-- Assinatura 4 (começa em 13 de outubro de 2024, válida até 13 de novembro de 2024)
INSERT INTO Subscription (code, startDate, endDate, client_Code, application_Code)
VALUES (4, '2024-10-13', '2024-11-13', 4, 4);

-- Assinatura 5 (começa em 14 de outubro de 2024, válida até 14 de novembro de 2024)
INSERT INTO Subscription (code, startDate, endDate, client_Code, application_Code)
VALUES (5, '2024-10-14', '2024-11-14', 5, 5);



-- Inserindo pagamentos para as assinaturas ativas
-- Vamos supor que cada pagamento é feito na data de início da assinatura e cobre o período até a data de término.

-- Pagamento para Assinatura 1 (10 de outubro de 2024)
INSERT INTO Payment (code, amount, paymentDate, subscription_Code)
VALUES (1, 29.99, '2024-10-10', 1);

-- Pagamento para Assinatura 2 (12 de outubro de 2024)
INSERT INTO Payment (code, amount, paymentDate, subscription_Code)
VALUES (2, 49.99, '2024-10-12', 2);

-- Pagamento para Assinatura 3 (15 de outubro de 2024)
INSERT INTO Payment (code, amount, paymentDate, subscription_Code)
VALUES (3, 19.99, '2024-10-15', 3);

-- Pagamento para Assinatura 4 (13 de outubro de 2024)
INSERT INTO Payment (code, amount, paymentDate, subscription_Code)
VALUES (4, 14.99, '2024-10-13', 4);

-- Pagamento para Assinatura 5 (14 de outubro de 2024)
INSERT INTO Payment (code, amount, paymentDate, subscription_Code)
VALUES (5, 24.99, '2024-10-14', 5);