USE `photo-db`;

create table if not exists photos(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    photo_id VARCHAR(50),
    dimensions VARCHAR(50),
    color VARCHAR(50),
    copies INTEGER,
    framing VARCHAR(50),
    gift_wrap VARCHAR(50)
);