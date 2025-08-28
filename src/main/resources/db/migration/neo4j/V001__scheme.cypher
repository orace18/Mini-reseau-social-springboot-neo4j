CREATE CONSTRAINT user_id IF NOT EXISTS
FOR (u:User) REQUIRE u.id IS UNIQUE;

CREATE CONSTRAINT post_id IF NOT EXISTS
FOR (p:Post) REQUIRE p.id IS UNIQUE;

CREATE CONSTRAINT comment_id IF NOT EXISTS
FOR (c:Comment) REQUIRE c.id IS UNIQUE;

CREATE INDEX post_created_at IF NOT EXISTS FOR (p:Post) ON (p.createdAt);
CREATE INDEX comment_created_at IF NOT EXISTS FOR (c:Comment) ON (c.createdAt);

/* (Optionnel) index de propriétés de relation (Neo4j 5+) si tu filtres par dates de LIKE/FOLLOW */
CREATE INDEX like_created_at IF NOT EXISTS
FOR ()-[r:LIKES]-() ON (r.createdAt);
CREATE INDEX follow_created_at IF NOT EXISTS
FOR ()-[r:FOLLOWS]-() ON (r.createdAt);
