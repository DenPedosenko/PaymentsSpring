-- -----------------------------------------------------
-- Table `Payments`.`user_types`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS user_types (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL
);

-- -----------------------------------------------------
-- Table `Payments`.`user_status`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS user_statuses (
  `id`      INTEGER PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL
  );

-- -----------------------------------------------------
-- Table `Payments`.`localized_user_status`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS localized_user_statuses (
  `id` INTEGER NOT NULL,
  `locale` varchar(5) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  primary key (id, locale),
  FOREIGN KEY (`id`) REFERENCES `user_statuses` (`id`) ON DELETE RESTRICT
   );

-- -----------------------------------------------------
-- Table `Payments`.`user`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS users (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `last_name` VARCHAR(50) NULL,
  `first_name` VARCHAR(50) NULL,
  `email` VARCHAR(100) NOT NULL,
  `user_password` VARCHAR(68) NOT NULL,
  `user_type_id` INT NOT NULL,
  `user_status_id` INT NOT NULL,
    FOREIGN KEY (`user_type_id`) REFERENCES `user_types` (`id`) ON DELETE RESTRICT,
    FOREIGN KEY (`user_status_id`) REFERENCES `user_statuses` (`id`) ON DELETE RESTRICT
    );
    
-- -----------------------------------------------------
-- Table `Payments`.`payment_status`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS payment_statuses (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL
  );
  -- -----------------------------------------------------
-- Table `Payments`.`localized_payment_status`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS localized_payment_statuses (
  `id` INTEGER NOT NULL,
  `locale` varchar(5) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  primary key (id, locale),
  FOREIGN KEY (`id`) REFERENCES `payment_statuses` (`id`) ON DELETE RESTRICT
  );
-- -----------------------------------------------------
-- Table `Payments`.`payment_type`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS payment_types (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL
  );
  
-- -----------------------------------------------------
-- Table `Payments`.`localized_payment_type`
-- -----------------------------------------------------
  CREATE TABLE IF NOT EXISTS localized_payment_types (
  `id` INTEGER NOT NULL,
  `locale` varchar(5) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  primary key (id, locale),
  FOREIGN KEY (`id`) REFERENCES `payment_types` (`id`) ON DELETE RESTRICT
  );


-- -----------------------------------------------------
-- Table `Payments`.`account_status`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS account_statuses (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL
  );

-- -----------------------------------------------------
-- Table `Payments`.`localized_account_status`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS localized_account_statuses (
   `id` INTEGER NOT NULL,
  `locale` varchar(5) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  primary key (id, locale),
  FOREIGN KEY (`id`) REFERENCES `account_statuses` (`id`) ON DELETE RESTRICT
  );


-- -----------------------------------------------------
-- Table `Payments`.`account`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS accounts (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `user_id` INT NOT NULL,
  `account_status_id` INT NOT NULL,
  `balance` DECIMAL (10, 2) NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`account_status_id`) REFERENCES `account_statuses` (`id`) ON DELETE RESTRICT
    );
    
-- -----------------------------------------------------
-- Table `Payments`.`localized_accounts`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS localized_accounts (
  `id` INTEGER NOT NULL,
  `locale` varchar(5) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  primary key (id, locale),
  FOREIGN KEY (`id`) REFERENCES `accounts` (`id`) ON DELETE RESTRICT
  );



-- -----------------------------------------------------
-- Table `Payments`.`payment`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS payments (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `creating_date` DATETIME NOT NULL,
  `payment_type_id` INT NOT NULL,
  `payment_status_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `account_id` INT NOT NULL,
  `amount` Decimal(10,2) NOT NULL,
    FOREIGN KEY (`payment_type_id`) REFERENCES `payment_types` (`id`) ON DELETE NO ACTION,
    FOREIGN KEY (`payment_status_id`) REFERENCES `payment_statuses` (`id`) ON DELETE NO ACTION,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION,
    FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE NO ACTION
    );
    
-- -----------------------------------------------------
-- Table `Payments`.`request_status`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS request_statuses (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL
  );
  
-- -----------------------------------------------------
-- Table `Payments`.`request_status`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS localized_request_statuses (
 `id` INTEGER NOT NULL,
  `locale` varchar(5) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  primary key (id, locale),
  FOREIGN KEY (`id`) REFERENCES `request_statuses` (`id`) ON DELETE RESTRICT
  );

-- -----------------------------------------------------
-- Table `Payments`.`requests`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS requests (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `creating_date` DATETIME NOT NULL,
  `user_id` INT NOT NULL,
  `account_id` INT NOT NULL,
  `request_status_id` INT NOT NULL,
    FOREIGN KEY (`request_status_id`) REFERENCES `payment_statuses` (`id`) ON DELETE RESTRICT,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT,
    FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE RESTRICT
    );

-- -----------------------------------------------------
-- Table `Payments`.`card`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS cards (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `card_number` VARCHAR(16) NOT NULL,
  `exp_date` VARCHAR(5) NOT NULL,
  `cvv` VARCHAR(3) NOT NULL,
  `account_id` INT NOT NULL,
    FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE CASCADE
    );