-- incident baza
CREATE DATABASE IF NOT EXISTS incident_db;
CREATE USER IF NOT EXISTS 'incident_user'@'%' IDENTIFIED BY 'incident_pass';
GRANT ALL PRIVILEGES ON incident_db.* TO 'incident_user'@'%';

-- moderation baza
CREATE DATABASE IF NOT EXISTS moderation_db;
CREATE USER IF NOT EXISTS 'moderation_user'@'%' IDENTIFIED BY 'moderation_pass';
GRANT ALL PRIVILEGES ON moderation_db.* TO 'moderation_user'@'%';

-- user baza
CREATE DATABASE IF NOT EXISTS user_db;
CREATE USER IF NOT EXISTS 'user_user'@'%' IDENTIFIED BY 'user_pass';
GRANT ALL PRIVILEGES ON user_db.* TO 'user_user'@'%';



FLUSH PRIVILEGES;