CREATE TABLE IF NOT EXISTS Application (
  code BIGSERIAL PRIMARY KEY, 
  name VARCHAR(255) NOT NULL, 
  monthly_fee FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS Client (
  code BIGSERIAL PRIMARY KEY, 
  name VARCHAR(255) NOT NULL, 
  email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Subscription (
  code BIGSERIAL PRIMARY KEY, 
  start_date DATE NOT NULL, 
  end_date DATE NOT NULL, 
  client_code BIGINT NOT NULL, 
  application_code BIGINT NOT NULL, 
  FOREIGN KEY (client_code) REFERENCES Client(code), 
  FOREIGN KEY (application_code) REFERENCES Application(code)
);

CREATE TABLE IF NOT EXISTS Promotion (
  code BIGSERIAL PRIMARY KEY, 
  discount_percentage FLOAT NOT NULL, 
  extra_days INT NOT NULL, 
  days_required INT NOT NULL, 
  application_code BIGINT NOT NULL, 
  FOREIGN KEY (application_code) REFERENCES Application(code)
);

CREATE TABLE IF NOT EXISTS Payment (
  code BIGSERIAL PRIMARY KEY, 
  amount DECIMAL NOT NULL, 
  payment_date DATE NOT NULL, 
  deal_code VARCHAR(255), 
  subscription_code BIGINT NOT NULL, 
  FOREIGN KEY (subscription_code) REFERENCES Subscription(code)
);

CREATE TABLE IF NOT EXISTS "User" (
  username VARCHAR(255) PRIMARY KEY, 
  password VARCHAR(255) NOT NULL
);