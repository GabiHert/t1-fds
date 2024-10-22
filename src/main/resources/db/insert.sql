-- Inserindo Clientes
INSERT INTO Client (code, name, email) VALUES
(1, 'Alice Silva', 'alice.silva@example.com'),
(2, 'Bruno Costa', 'bruno.costa@example.com'),
(3, 'Carla Mendes', 'carla.mendes@example.com'),
(4, 'Diego Oliveira', 'diego.oliveira@example.com'),
(5, 'Eduarda Santos', 'eduarda.santos@example.com'),
(6, 'Felipe Almeida', 'felipe.almeida@example.com'),
(7, 'Gabriela Lima', 'gabriela.lima@example.com'),
(8, 'Henrique Rocha', 'henrique.rocha@example.com'),
(9, 'Isabela Ferreira', 'isabela.ferreira@example.com'),
(10, 'João Pereira', 'joao.pereira@example.com')
ON CONFLICT (code) DO NOTHING;

-- Inserindo Aplicativos
INSERT INTO Application (code, name, monthlyFee) VALUES
(1, 'App A', 9.99),
(2, 'App B', 19.99),
(3, 'App C', 29.99),
(4, 'App D', 15.99),
(5, 'App E', 24.99)
ON CONFLICT (code) DO NOTHING;

-- Inserindo Assinaturas
-- Assinaturas válidas
INSERT INTO Subscription (code, startDate, endDate, client_code, application_code) VALUES
(1, '2024-10-01', '2025-10-01', 1, 1),  -- Alice Silva - App A (válida)
(2, '2024-10-15', '2025-04-15', 2, 2),  -- Bruno Costa - App B (válida)
(3, '2024-10-05', '2025-10-05', 3, 3)  -- Carla Mendes - App C (válida)
ON CONFLICT (code) DO NOTHING;

-- Assinaturas inválidas
INSERT INTO Subscription (code, startDate, endDate, client_code, application_code) VALUES
(4, '2024-10-01', '2024-09-30', 4, 4),  -- Diego Oliveira - App D (inválida)
(5, '2024-10-01', '2024-10-01', 5, 5)  -- Eduarda Santos - App E (inválida)
ON CONFLICT (code) DO NOTHING;

-- Inserindo Promoções
INSERT INTO Promotion (code, discountPercentage, extraDays, monthsRequired, subscription_code) VALUES
(1, 40, 0, 12, 1),  -- Promoção de 40% de desconto para pagamento anual (Assinatura 1)
(2, 0, 45, 0, 2)   -- Pague 30 e ganhe 45 dias (Assinatura 2)
ON CONFLICT (code) DO NOTHING;


-- Inserindo Pagamentos
INSERT INTO Payment (code, amount, paymentDate, dealCode, subscription_code) VALUES
(1, 9.99, '2024-10-01', 'DEAL001', 1),  -- Pagamento válido para assinatura de Alice Silva
(2, 19.99, '2024-10-15', 'DEAL002', 2), -- Pagamento válido para assinatura de Bruno Costa
(3, 29.99, '2024-10-05', 'DEAL003', 3), -- Pagamento válido para assinatura de Carla Mendes
(4, 15.99, '2024-10-01', 'DEAL004', 4), -- Pagamento inválido para assinatura de Diego Oliveira
(5, 24.99, '2024-10-01', 'DEAL005', 5) -- Pagamento inválido para assinatura de Eduarda Santos
ON CONFLICT (code) DO NOTHING;

