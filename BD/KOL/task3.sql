WITH totals AS (
	SELECT name, COALESCE(SUM(price), 0) as total
  	FROM Company LEFT JOIN Sales
  	ON Company.name = Sales.buyer
  	GROUP BY name
),
top3 AS (
	SELECT S.id, S.buyer, S.price
 	FROM Sales S
  	WHERE S.id IN (SELECT tmp.id
                 FROM Sales tmp
                 WHERE S.buyer = tmp.buyer
                 ORDER BY tmp.price DESC
                 LIMIT 3
                 ) 
),
top3Totals AS (
 	SELECT S.buyer as name, SUM(S.price) as total
 	FROM top3 S
 	GROUP BY S.buyer
)
SELECT totals.name
FROM totals LEFT JOIN top3Totals
ON totals.name = top3Totals.name
WHERE totals.total * 0.9 <= COALESCE(top3Totals.total, 0)
ORDER BY totals.name ASC;
  