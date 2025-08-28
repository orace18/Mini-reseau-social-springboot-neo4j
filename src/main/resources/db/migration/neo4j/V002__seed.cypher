UNWIND range(1,5) AS i
MERGE (u:User {id: 'U'+i, username: 'user'+i, name: 'User '+i, createdAt: datetime()})
WITH collect(u) AS users
FOREACH (u IN users | FOREACH (v IN users |
CASE WHEN u<>v AND rand()<0.5 THEN
MERGE (u)-[:FOLLOWS {createdAt: datetime()}]->(v)
ELSE [] END))

WITH users
UNWIND range(1,20) AS j
WITH users, j, users[toInteger(rand()*size(users))] AS a
CREATE (p:Post {id: randomUUID(), text: 'Post '+j, createdAt: datetime() - duration({hours: toInteger(rand()*72)})})
MERGE (a)-[:POSTED]->(p);
