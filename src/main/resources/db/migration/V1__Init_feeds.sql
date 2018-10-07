--SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
--SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
--SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

--CREATE SCHEMA IF NOT EXISTS "feeds" DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
--USE "feeds" ;

-- -----------------------------------------------------
-- Table "feeds"."feeds"
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `feeds` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `url` VARCHAR(255) NULL,
  `title` VARCHAR(45) NULL,
  `last_update` DATETIME NULL,
  `feed_name` VARCHAR(255) NULL,
  PRIMARY KEY (`id`));
--ENGINE = InnoDB
--DEFAULT CHARACTER SET = utf8
--COLLATE = utf8_general_ci;

CREATE INDEX FEEDNAME ON `feeds`(`feed_name` ASC);


-- -----------------------------------------------------
-- Table "feeds"."items"
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `items` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `feed_id` INT NULL,
  `title` VARCHAR(255) NULL,
  `link` VARCHAR(255) NULL,
  `description` TEXT NULL,
  `published` DATETIME NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_items_1`
    FOREIGN KEY (`feed_id`)
    REFERENCES `feeds` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
--ENGINE = InnoDB
--DEFAULT CHARACTER SET = utf8
--COLLATE = utf8_general_ci;

CREATE INDEX fk_items_1 ON `items`(`feed_id` ASC);
--SET SQL_MODE=@OLD_SQL_MODE;
--SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
--SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
