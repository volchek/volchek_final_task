CREATE DATABASE  IF NOT EXISTS `likeit2` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `likeit2`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: likeit2
-- ------------------------------------------------------
-- Server version	5.1.45-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `answermarks`
--

DROP TABLE IF EXISTS `answermarks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answermarks` (
  `answerId` int(11) unsigned NOT NULL COMMENT 'ID ответа',
  `userId` int(11) unsigned NOT NULL COMMENT 'Пользователь, оценивший ответ',
  `mark` tinyint(4) NOT NULL COMMENT 'Оценка ответа (от 1 до 10 баллов)',
  `datetime` datetime NOT NULL COMMENT 'Дата и время выставления оценки',
  PRIMARY KEY (`answerId`,`userId`),
  KEY `fkAnswerId_idx` (`answerId`),
  KEY `fkUserId_idx` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Оценки, выставленные ответам.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answermarks`
--

LOCK TABLES `answermarks` WRITE;
/*!40000 ALTER TABLE `answermarks` DISABLE KEYS */;
INSERT INTO `answermarks` VALUES (1,1,3,'2017-10-23 21:13:45'),(2,1,2,'2017-10-23 21:15:03'),(3,1,4,'2017-10-29 15:53:47'),(4,2,5,'2017-12-04 12:20:09'),(5,3,4,'2017-12-04 16:15:57'),(6,9,3,'2017-12-04 23:40:00'),(7,11,3,'2017-11-30 18:27:15'),(8,11,5,'2017-11-30 18:30:00'),(9,5,3,'2017-11-30 22:22:23'),(10,10,5,'2017-11-23 15:10:10'),(11,10,5,'2017-11-23 15:11:10'),(12,6,5,'2017-12-01 23:09:54'),(13,2,5,'2017-12-01 23:34:09');
/*!40000 ALTER TABLE `answermarks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answers` (
  `answerId` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID ответа',
  `questionId` int(11) unsigned NOT NULL COMMENT 'Id вопроса, на который дан ответ',
  `answerText` varchar(5000) NOT NULL COMMENT 'Текст ответа',
  `userId` int(11) NOT NULL COMMENT 'Автор ответа на вопрос',
  `creationDatetime` datetime NOT NULL COMMENT 'Дата и время ответа на вопрос',
  PRIMARY KEY (`answerId`),
  KEY `fkUserId_idx` (`userId`),
  KEY `fkQuestionId_idx` (`questionId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='Ответы пользователей на вопросы';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (1,1,'Если Ваша структрура является POD типом, то ее записать можно легко. Если же нет... тогда придется делать ручную сериализацию.',6,'2017-10-23 16:53:18'),(2,1,'Покажите объявление структуры. Чтоб видеть какие там данные',10,'2017-10-23 19:20:00'),(3,2,'можно поставить RealTime OS (QNX к примеру), там будут гаратнии на sleep.',9,'2017-10-29 13:53:17'),(4,3,'В стандарте есть пример: template<class T> constexpr T pi = T(3.1415926535897932385L); template<class T> T circular_area(T r) {   return pi<T> * r * r; } Здесь шаблон переменной позволяет получить константу нужного размера - float/double/etc.',6,'2017-12-03 20:15:11'),(5,3,'Тут можно заметить, что при объявлении шаблона переменной тип этой переменной совсем не обязан совпадать с параметром шаблона или вообще быть как-то связан с параметром(-ами) шаблона. Большинство тривиальных примеров шаблонов переменных обычно дают переменной тот же самый тип, что использовался в параметре шаблона, что может создать ложное впечатление, как будто это требуется. На самом деле шаблонная переменная может иметь любой тип.',9,'2017-12-04 10:09:22'),(6,3,'Также можно добавить, что возможность включения статических членов-данных в шаблоны классов существовала в языке С++ с самого начала стандартизованных времен, т.е. в С++98. Это позволяло реализовывать \"шаблоны переменных\" уже тогда, пользуясь фактически той же самой техникой, которая в С++98 применялась для \"шаблонных typedef\", т.е. через помещение типов или переменных в \"обертку\" шаблонного класса. В частности, вышеприведенный пример может быть реализован на классическом С++98',2,'2017-12-04 13:54:09'),(7,4,'Проблема в том, что cmd.exe не обрабатывает вывод приложений с графическим интерфейсом. При запуске оболочка проверяет флажок в заголовке исполняемого файла, и если приложение оконное, то оно запускается не блокируя консоль. После этого можно запустить другое приложение, выполнять команды, либо вовсе закрыть окно консоли. Соответственно, если после этого в консоли будут выводиться сообщения, то будет неясно от какого процесса они исходят.',10,'2017-11-30 02:30:10'),(8,4,'Выводить сообщение об ошибке в графическом интерфейсе. Раз все приложение оконное, то и сообщение логично показывать в интерфейсе.',6,'2017-11-30 09:17:23'),(9,4,'Сделать приложение консольным (изменить тип вывода в настройках проекта Visual Studio). В этом случае будет работать вывод в консоль. Недостаток в том, что окно консоли появится даже при запуске в проводнике. Консоль можно скрывать с помощью ShowWindow, но полностью избежать его появления не получится.',7,'2017-11-30 14:22:56'),(10,5,'my $x = <STDIN>;',9,'2017-11-23 12:34:10'),(11,5,'А так лучше: chomp ($x = <STDIN>);',8,'2017-11-23 12:56:09'),(12,6,'Boost.TypeTraits, например',1,'2017-12-01 15:56:08'),(13,6,'Можно использовать темплейт с переменным числом параметров',13,'2017-12-01 19:40:55');
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `keywords`
--

DROP TABLE IF EXISTS `keywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keywords` (
  `keywordId` int(2) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID ключевого слова (сами ключевые слова могут быть длинными, поэтому ввожу искусственный первичный ключ)',
  `keyword` varchar(100) NOT NULL COMMENT 'Ключевые слова для вопросов и ответов (например, "запись в файл", "рекурсия", "бесконечный цикл" и т.п.). Будут нужны для поиска подходящих вопросов и ответов.',
  PRIMARY KEY (`keywordId`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 COMMENT='Ключевые слова, по которым можно будет искать вопросы';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keywords`
--

LOCK TABLES `keywords` WRITE;
/*!40000 ALTER TABLE `keywords` DISABLE KEYS */;
INSERT INTO `keywords` VALUES (1,'write'),(2,'read'),(3,'file'),(4,'memory'),(5,'algorithm'),(6,'graph'),(7,'cycle'),(8,'condition'),(9,'switch'),(10,'database'),(11,'connection'),(12,'tag'),(13,'function'),(14,'console'),(15,'pattern'),(16,'recursion'),(17,'string'),(18,'data structures'),(19,'template'),(20,'generic'),(21,'tomcat'),(22,'inner class'),(23,'OOP'),(24,'inheritance'),(25,'linker'),(26,'compiler'),(27,'interpretator'),(28,'regex'),(29,'http'),(30,'eclipse'),(31,'animation'),(32,'exception'),(33,'pointer'),(34,'form'),(35,'list'),(36,'maven'),(37,'gui'),(38,'servlet'),(39,'jsp'),(40,'dom'),(41,'session'),(42,'socket'),(43,'thread'),(44,'debug'),(45,'IDE'),(46,'frontend'),(47,'IP'),(48,'SSL'),(49,'JDBC'),(50,'utf-8'),(51,'framework'),(52,'video'),(53,'OS'),(54,'ftp'),(55,'audio'),(56,'image'),(57,'unicode'),(58,'brouser'),(59,'boost'),(60,'jar'),(61,'library'),(62,'STL'),(63,'TCP'),(64,'process'),(65,'graphics'),(66,'encoding'),(67,'menu'),(68,'matrix'),(69,'cmake'),(70,'git'),(71,'locale'),(72,'machine learning'),(73,'disign pattern'),(74,'constructor'),(75,'destructor'),(76,'memory leak'),(77,'class'),(78,'overloading'),(79,'operator'),(80,'method');
/*!40000 ALTER TABLE `keywords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `languages`
--

DROP TABLE IF EXISTS `languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `languages` (
  `languageId` smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Условный код языка программирования или его версии (например, С++, С++11, С++14, Java7, Java8 и т.п.). Не делаю первичным ключом само название языка, так как оно может быть длинное (например, Javascript)',
  `language` varchar(30) NOT NULL COMMENT 'Название языка программирования',
  PRIMARY KEY (`languageId`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='Список языков программирования, по которым задаются вопросы';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `languages`
--

LOCK TABLES `languages` WRITE;
/*!40000 ALTER TABLE `languages` DISABLE KEYS */;
INSERT INTO `languages` VALUES (1,'C'),(2,'C++'),(3,'C++11'),(4,'C++14'),(5,'Java'),(6,'Java7'),(7,'Java8'),(8,'PHP'),(9,'JavaScript'),(10,'C#'),(11,'Python'),(12,'Python3'),(13,'Python2'),(14,'Perl'),(15,'Swift'),(16,'HTML'),(17,'CSS'),(18,'SQL'),(19,'Scratch'),(20,'Ada'),(21,'Assembler'),(22,'AutoHotkey'),(23,'Bash'),(24,'BASIC'),(25,'Caml'),(26,'Cobol'),(27,'CoffeeScript'),(28,'CUDA'),(29,'Delphi'),(30,'ECMAScript'),(31,'Erlang'),(32,'F#'),(33,'Fortran'),(34,'FoxPro'),(35,'Haskell'),(36,'Icon'),(37,'Julia'),(38,'Kotlin'),(39,'LaTeX'),(40,'Lisp'),(41,'Lua'),(42,'MATLAB'),(43,'Mathematica'),(44,'Object Pascal'),(45,'Objective-C'),(46,'OCaml'),(47,'Octave'),(48,'Pascal'),(49,'PostScript'),(50,'PowerShell'),(51,'Prolog'),(52,'R'),(53,'Ruby'),(54,'Scala'),(55,'Simula'),(56,'Smalltalk'),(57,'TeX'),(58,'T-SQL'),(59,'TypeScript'),(60,'Unix shell'),(61,'UNITY'),(62,'Visual Basic'),(63,'Wolfram Language'),(64,'XML');
/*!40000 ALTER TABLE `languages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionmarks`
--

DROP TABLE IF EXISTS `questionmarks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionmarks` (
  `questionId` int(11) unsigned NOT NULL COMMENT 'ID вопроса',
  `userId` int(11) unsigned NOT NULL COMMENT 'Пользователь, оценивший вопрос',
  `mark` tinyint(4) NOT NULL COMMENT 'Оценка вопроса (от 1 до 10 баллов)',
  `datetime` datetime DEFAULT NULL COMMENT 'Дата и время выставления оценки',
  PRIMARY KEY (`questionId`,`userId`),
  KEY `fkQuestionId_idx` (`questionId`),
  KEY `fkUserId_idx` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Оценки, выставленные вопросам.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionmarks`
--

LOCK TABLES `questionmarks` WRITE;
/*!40000 ALTER TABLE `questionmarks` DISABLE KEYS */;
INSERT INTO `questionmarks` VALUES (1,3,5,'2017-10-23 22:18:32'),(1,6,4,'2017-10-23 22:15:01'),(1,8,5,'2017-10-25 10:12:43'),(1,10,5,'2017-10-24 08:10:00'),(1,11,4,'2017-10-25 15:15:07'),(1,13,4,'2017-10-24 13:20:01'),(2,6,2,'2017-10-29 16:10:07'),(2,10,3,'2017-10-29 18:15:47'),(3,1,0,'2017-12-03 19:10:14'),(3,6,0,'2017-12-03 21:40:50'),(3,8,0,'2017-12-04 00:13:13'),(4,1,1,'2017-11-30 00:10:10'),(4,12,2,'2017-11-30 09:13:01'),(5,1,1,'2017-11-23 14:53:14'),(5,10,1,'2017-11-24 07:03:18'),(6,1,3,'2017-12-01 14:20:01'),(6,2,2,'2017-12-01 17:23:18'),(6,8,4,'2017-12-01 19:13:01'),(7,1,5,'2017-12-06 10:10:10'),(7,10,5,'2017-12-06 10:13:19'),(8,2,4,'2017-12-09 17:32:15'),(8,10,3,'2017-12-09 23:20:09');
/*!40000 ALTER TABLE `questionmarks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `questionId` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Уникальный id вопроса',
  `title` varchar(300) NOT NULL,
  `text` varchar(3000) NOT NULL COMMENT 'Текст вопроса',
  `userId` int(11) NOT NULL COMMENT 'Автор вопроса',
  `creationDatetime` datetime NOT NULL COMMENT 'Дата и время создания вопроса',
  PRIMARY KEY (`questionId`),
  KEY `fkUserId_idx` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='Заданные вопросы';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'Как записать структуру в бинарном виде?','Цель:пытаюсь записать в bit.dat файл,структуру данных в бинарном виде,для сжатия файла,не получается. Использую дерево Хафмана,сначала считывается текст из файла text.txt,затем все добавляется в дерево Хафмана,и каждого символа двоичный код(созданный при помощи Хафмана)записывается в table.txt,после с помощью этого всего записываю в bit.dat в \"битовом\" формате,но не получается,пишетто все,и правильно как надо,но размер файла больше чем обычный text.txt файл.',1,'2017-10-23 16:33:08'),(2,'Многопоточность','Стандартная библиотека. C++ 11. Есть такой способ усыпить поток std::this_thread::sleep_until Есть задача: обеспечить сон с точностью до микросекунды. Путем экспериментов выяснилось, что сам sleep дает погрешность ~56 микросекунд. Таким образом получить сон с точностью до микросекунды с помощью sleep-а - проблема. Есть ли какие то другие способы выполнить задачу?',1,'2017-10-29 13:10:07'),(3,'Что такое variadic templates?','Изучая новый стандарт C++, натолкнулся на нововведение «шаблоны переменных». Синтаксис у шаблона следующий: template < typename T >',5,'2017-12-03 18:15:59'),(4,'Вывод хелпа с параметрами','Приложение может запускаться с параметрами. Есть необходимость вывести хелп с параметрами в этой же консоли, где было запущено приложение, если пользователь ошибся, скажем так. Как при запуске приложения в этой же консоли вывести сообщение? ',11,'2017-11-29 23:40:10'),(5,'Переменные','Как в переменную $x ввести значение с клавиатуры в PERL',10,'2017-11-23 10:06:14'),(6,'Как узнать количество аргументов?','Недавно был вопрос о том, как объявить функцию с n аргументами заданного типа. У меня возник противоположный вопрос. Как узнать количество аргументов функции?',6,'2017-12-01 12:56:08'),(7,'Хвостовая рекурсия','Никак не могу разобраться, чем друг от друга отличаются. Правильно ли я понимаю, принцип работы традиционной рекурсии - это только передача значения, поэтому традиционная не ограничивается размером стека и вполне может себе позволить зациклиться, в то время как хвостовая хранит в стеке адреса перехода, а также промежуточное значение, что может привести к переполнению стека, т.о. хвостовую можно рассмотреть как обычный цикл? Если так, то в чем преимущества традиционной или хвостовой?',6,'2017-12-05 22:10:34'),(8,'Односвязный список','Здравствуйте, мне дали задание, реализовать односвязный список на массиве. Я не совсем понимаю, что от меня требуется. Реализовывать стеки/деки/двусвязные и односвязные списки/очереди и т.д. умею, операции к ним тоже но с использованием структур',6,'2017-12-09 15:41:07'),(9,'Проблема...','У меня есть некий набор объектов: List<UserType> userTypes = сonfiguration.getUserTypes(); Если я обхожу его в цикле, то из объекта userType я могу вытащить свойства соответственно только класса UserType, но в этом листе также лежат и наследники UserType. Получается к ним никаким образом доступ не получить?',9,'2017-12-25 19:16:32'),(10,'Присваивание для строк','Проблема в том, что когда я присваиваю строке значение из файла strings в классе активности через String string = getResources().getString(R.string.test); все присваивается нормально, но в отдельном классе адаптера при попытке такой записи студия даже не предлагает в подсказках такой код, как же получить ссылку на ресурс в таком случае?',9,'2017-12-18 17:30:01'),(15,'Template','What is a template?<br>',1,'2018-01-17 00:16:50'),(16,'Template','Что такое шаблонная функция?<br>',1,'2018-01-17 00:25:53'),(24,'Рекурсия','Кто-нибудь знает, есть ли в Ruby рекурсия?<br>',1,'2018-01-18 13:36:20');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions2keywords`
--

DROP TABLE IF EXISTS `questions2keywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions2keywords` (
  `questionId` int(11) unsigned NOT NULL COMMENT 'ID вопроса',
  `keywordId` int(2) unsigned NOT NULL COMMENT 'ID ключевого слова',
  PRIMARY KEY (`keywordId`,`questionId`),
  KEY `fkQuestionId_idx` (`questionId`),
  KEY `fkKeywordId_idx` (`keywordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Сопоставление вопросов и ключевых слов в них.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions2keywords`
--

LOCK TABLES `questions2keywords` WRITE;
/*!40000 ALTER TABLE `questions2keywords` DISABLE KEYS */;
INSERT INTO `questions2keywords` VALUES (1,1),(1,3),(3,19),(4,14),(5,2),(5,14),(6,13),(7,16),(8,18),(9,23),(9,24),(10,17),(15,19),(16,13),(16,19),(24,16);
/*!40000 ALTER TABLE `questions2keywords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions2languages`
--

DROP TABLE IF EXISTS `questions2languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions2languages` (
  `questionId` int(11) unsigned NOT NULL COMMENT 'Код вопроса',
  `languageId` smallint(6) unsigned NOT NULL COMMENT 'Код языка программирования, по которому задан вопрос',
  PRIMARY KEY (`questionId`,`languageId`),
  KEY `fkQuestionId_idx` (`questionId`),
  KEY `fkLanguageId_idx` (`languageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Сопоставление вопросу языков программирования';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions2languages`
--

LOCK TABLES `questions2languages` WRITE;
/*!40000 ALTER TABLE `questions2languages` DISABLE KEYS */;
INSERT INTO `questions2languages` VALUES (1,2),(2,2),(6,2),(7,2),(15,2),(16,2),(2,3),(3,3),(6,3),(16,3),(3,4),(6,4),(16,4),(8,5),(9,5),(10,5),(8,6),(8,7),(4,10),(5,14),(24,53);
/*!40000 ALTER TABLE `questions2languages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `userId` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор',
  `surname` varchar(70) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT 'Фамилия',
  `name` varchar(70) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT 'Имя',
  `login` varchar(20) COLLATE utf8_bin NOT NULL COMMENT 'Уникальный логин (непустой)\n',
  `password` varchar(70) COLLATE utf8_bin NOT NULL COMMENT 'Пароль (непустой)',
  `registrationDate` datetime DEFAULT NULL COMMENT 'Дата и время регистрации',
  `birthdayDate` date DEFAULT NULL COMMENT 'Дата рождения',
  `accessLevel` enum('admin','user') CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'user' COMMENT 'Привилегии доступа (администратор или обычный пользователь)',
  `status` enum('student','trainer','guru') CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT 'Статус пользователя (студент, тренер или гуру)',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Путь к аватару',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `reserveEmail` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `isBanned` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userId`),
  KEY `login` (`login`),
  KEY `surname` (`surname`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Личная информация о пользователях.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Зайцев','Владимир','Zayatc','qwerty','2017-09-10 00:00:00','1991-10-10','user','student',NULL,'zayatc@tut.by','new_zayatc@tut.by',0),(2,'Копоть','Дмитрий','Dimych','asdfasdf','2017-11-29 00:00:00','1992-12-01','user','student',NULL,NULL,NULL,0),(3,'Мацко','Вероника','Olaniel','jklqwe','2017-09-15 00:00:00','1995-05-13','user','student',NULL,NULL,NULL,0),(4,'Довнар','Максим','Tzar','sdfwer','2017-09-30 00:00:00','1996-02-09','user','student',NULL,NULL,NULL,0),(5,'Ивановский','Валерий','Valerka','qweqwe78979','2017-09-30 00:00:00','1994-06-19','user','student',NULL,NULL,NULL,0),(6,'Высоких','Дмитрий','DimDimytch','qe789','2017-10-05 00:00:00','1992-11-25','user','student',NULL,NULL,NULL,0),(7,'Kacher','Ivan','Kacher','u798qwe','2017-10-24 00:00:00','1991-07-10','user','student','avatar1516269017609@tut.by','user1516269017906@tut.by','new_user1516269017906@tut.by',0),(8,'Аладьин','Андрей','user1992','qwe123','2017-10-20 00:00:00','1990-07-10','user','student',NULL,NULL,NULL,0),(9,'Андросов','Александр','Sashka','dsf7897','2017-09-18 00:00:00','1998-01-02','user','student',NULL,NULL,NULL,0),(10,'Дощечко','Виталий','smerch','re234','2017-09-12 00:00:00','1997-09-15','user','student',NULL,NULL,NULL,0),(11,'Шибалович','Иван','dancer','wewre23','2017-10-14 00:00:00','1995-03-29','user','student',NULL,NULL,NULL,0),(12,'Соколов','Максим','M.Sokolov','rte3t3','2017-09-02 00:00:00','1987-12-05','user','trainer',NULL,NULL,NULL,0),(13,'Шеремет','Петр','P.Sheremet','ret435','2017-09-02 00:00:00','1986-12-11','user','trainer',NULL,NULL,NULL,0),(14,'Беспалова','Татьяна','T.Bespalova','fh45345','2017-09-02 00:00:00','1985-05-10','user','trainer',NULL,NULL,NULL,0),(15,'Порицкий','Валерий','V.Poritsky','klk98','2017-09-02 00:00:00','1989-04-20','user','trainer',NULL,NULL,NULL,0),(16,'Волчек','Оксана','O_Volchek','lkjl8989','2017-09-01 00:00:00','1987-08-13','admin','student',NULL,NULL,NULL,0),(72,'Антонюк','Вероника','veronichka','qwertyQ1','2018-01-16 17:22:25','1982-03-05','user','student','','vifivifi@gmail.com',NULL,0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users2languages`
--

DROP TABLE IF EXISTS `users2languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users2languages` (
  `userId` int(11) unsigned NOT NULL COMMENT 'Идентификатор пользователя',
  `languageId` smallint(6) unsigned NOT NULL COMMENT 'Идентификатор языка программирования',
  `level` tinyint(4) NOT NULL COMMENT 'Уровень владения языком программирования (от 1 до 5 баллов)',
  PRIMARY KEY (`userId`,`languageId`),
  KEY `idUser_idx` (`userId`),
  KEY `fkLanguageId_idx` (`languageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Соответствие между пользователями...';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users2languages`
--

LOCK TABLES `users2languages` WRITE;
/*!40000 ALTER TABLE `users2languages` DISABLE KEYS */;
INSERT INTO `users2languages` VALUES (1,1,4),(1,2,4),(1,5,1),(1,10,2),(1,11,1),(2,1,3),(2,2,2),(3,1,1),(3,14,3),(4,1,2),(4,2,2),(4,10,4),(4,11,2),(5,1,3),(5,2,3),(5,9,3),(6,1,4),(6,15,1),(6,16,1),(7,11,2),(8,8,3),(8,9,3),(9,5,3),(10,14,2),(11,1,3),(11,10,3),(12,8,5),(12,9,5),(13,1,5),(13,2,5),(13,10,4),(13,16,2),(14,5,5),(15,11,5),(72,1,1),(72,2,1),(72,5,1),(72,8,2),(72,9,2),(72,10,1),(72,11,1),(72,14,1),(72,15,1),(72,16,2),(72,17,1),(72,18,1);
/*!40000 ALTER TABLE `users2languages` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-19 23:10:22
