CREATE TABLE IF NOT EXISTS Application (
                                           code BIGINT PRIMARY KEY,
                                           name VARCHAR(255) NOT NULL,
                                           monthlyFee FLOAT NOT NULL
);


CREATE TABLE IF NOT EXISTS Client (
                                      code BIGINT PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
                                      email VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS Promotion (
                                         code BIGINT PRIMARY KEY,
                                         discountPercentage FLOAT NOT NULL,
                                         extraDays INT NOT NULL,
                                         monthsRequired INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Subscription (
                                            code BIGINT PRIMARY KEY,
                                            startDate DATE NOT NULL,
                                            endDate DATE NOT NULL,
                                            client_code BIGINT,
                                            application_code BIGINT,
                                            CONSTRAINT fk_client FOREIGN KEY (client_code) REFERENCES Client(code),
                                            CONSTRAINT fk_application FOREIGN KEY (application_code) REFERENCES Application(code)
);


CREATE TABLE IF NOT EXISTS Payment (
                                       code BIGINT PRIMARY KEY,
                                       amount DECIMAL NOT NULL,
                                       paymentDate DATE NOT NULL,
                                       dealCode VARCHAR(255),
                                       subscription_code BIGINT,
                                       CONSTRAINT fk_subscription FOREIGN KEY (subscription_code)
                                           REFERENCES Subscription(code) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "User" (
                                      username VARCHAR(255) PRIMARY KEY,
                                      password VARCHAR(255) NOT NULL
);
