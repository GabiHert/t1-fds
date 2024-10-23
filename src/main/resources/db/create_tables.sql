CREATE TABLE IF NOT EXISTS Application (
  code BIGSERIAL PRIMARY KEY, 
  name VARCHAR(255) NOT NULL, 
  monthlyFee FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS Client (
  code BIGSERIAL PRIMARY KEY, 
  name VARCHAR(255) NOT NULL, 
  email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Subscription (
  code BIGSERIAL PRIMARY KEY, 
  startDate DATE NOT NULL, 
  endDate DATE NOT NULL, 
  clientCode BIGINT NOT NULL, 
  applicationCode BIGINT NOT NULL, 
  FOREIGN KEY (clientCode) REFERENCES Client(code), 
  FOREIGN KEY (applicationCode) REFERENCES Application(code)
);

CREATE TABLE IF NOT EXISTS Promotion (
  code BIGSERIAL PRIMARY KEY, 
  discountPercentage FLOAT NOT NULL, 
  extraDays INT NOT NULL, 
  monthsRequired INT NOT NULL, 
  applicationCode BIGINT NOT NULL, 
  FOREIGN KEY (applicationCode) REFERENCES Application(code)
);

CREATE TABLE IF NOT EXISTS Payment (
  code BIGSERIAL PRIMARY KEY, 
  amount DECIMAL NOT NULL, 
  paymentDate DATE NOT NULL, 
  dealCode VARCHAR(255), 
  subscriptionCode BIGINT NOT NULL, 
  FOREIGN KEY (subscriptionCode) REFERENCES Subscription(code)
);

CREATE TABLE IF NOT EXISTS "User" (
  username VARCHAR(255) PRIMARY KEY, 
  password VARCHAR(255) NOT NULL
);