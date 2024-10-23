-- Inserindo Clientes
INSERT INTO Client (name, email) VALUES
('Alice Silva', 'alice.silva@example.com'),
('Bruno Costa', 'bruno.costa@example.com'),
('Carla Mendes', 'carla.mendes@example.com'),
('Diego Oliveira', 'diego.oliveira@example.com'),
('Eduarda Santos', 'eduarda.santos@example.com'),
('Felipe Almeida', 'felipe.almeida@example.com'),
('Gabriela Lima', 'gabriela.lima@example.com'),
('Henrique Rocha', 'henrique.rocha@example.com'),
('Isabela Ferreira', 'isabela.ferreira@example.com'),
('João Pereira', 'joao.pereira@example.com')
ON CONFLICT (code) DO NOTHING;

-- Inserindo Aplicativos
INSERT INTO Application (name, monthlyFee) VALUES
('App A', 9.99),
('App B', 19.99),
('App C', 29.99),
('App D', 15.99),
('App E', 24.99)
ON CONFLICT (code) DO NOTHING;

-- Inserindo Assinaturas
-- Assinaturas válidas
INSERT INTO Subscription (startDate, endDate, clientCode, applicationCode) VALUES
 ('2024-10-01', '2025-10-01', 1, 1),  -- Alice Silva - App A (válida)
('2024-10-15', '2025-04-15', 2, 2),  -- Bruno Costa - App B (válida)
('2024-10-05', '2025-10-05', 3, 3)  -- Carla Mendes - App C (válida)
ON CONFLICT (code) DO NOTHING;

-- Assinaturas inválidas
INSERT INTO Subscription (startDate, endDate, clientCode, applicationCode) VALUES
('2024-10-01', '2024-09-30', 4, 4),  -- Diego Oliveira - App D (inválida)
('2024-10-01', '2024-10-01', 5, 5)  -- Eduarda Santos - App E (inválida)
ON CONFLICT (code) DO NOTHING;

-- Inserindo Promoções
INSERT INTO Promotion (discountPercentage, extraDays, monthsRequired, applicationCode) VALUES
(40, 0, 12, 1),  -- Promoção de 40% de desconto para App A
(0, 45, 0, 2)   -- Pague 30 e ganhe 45 dias para App B
ON CONFLICT (code) DO NOTHING;

-- Inserindo Pagamentos
INSERT INTO Payment (amount, paymentDate, dealCode, subscriptionCode) VALUES
(9.99, '2024-10-01', 'DEAL001', 1),  -- Pagamento válido para assinatura de Alice Silva
(9.99, '2024-10-15', 'DEAL002', 2), -- Pagamento válido para assinatura de Bruno Costa
(29.99, '2024-10-05', 'DEAL003', 3), -- Pagamento válido para assinatura de Carla Mendes
(15.99, '2024-10-01', 'DEAL004', 4), -- Pagamento inválido para assinatura de Diego Oliveira
(24.99, '2024-10-01', 'DEAL005', 5) -- Pagamento inválido para assinatura de Eduarda Santos
ON CONFLICT (code) DO NOTHING;
