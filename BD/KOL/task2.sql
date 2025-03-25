WITH good_sales AS (
	SELECT company as name, COUNT(DISTINCT shareholder) as good
 	FROM Ownership JOIN Sales
	ON company = seller AND buyer = shareholder
 	GROUP BY company
),
shareholders AS (
 	SELECT Company.name as name, COUNT(DISTINCT shareholder) as total
  	FROM Company LEFT JOIN Ownership
  	ON Company.name = Ownership.company
  	GROUP BY Company.name
),
merged AS (
  SELECT shareholders.name, COALESCE(good_sales.good, 0) as good, shareholders.total
  FROM shareholders LEFT JOIN good_sales
  ON shareholders.name = good_sales.name
)
SELECT name FROM merged
WHERE good = total
ORDER BY name ASC;