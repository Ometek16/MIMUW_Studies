WITH sell AS (
  SELECT seller, SUM(price) as total
  FROM Sales
  GROUP BY seller
),
buy AS (
  SELECT buyer, SUM(price) as total
  FROM Sales
  GROUP BY buyer
)
SELECT 
	A.name, 
	COALESCE(A.total, 0) - COALESCE(buy.total, 0) as net_total
FROM 
	(SELECT * 
	FROM Company 
	LEFT JOIN sell ON Company.name = sell.seller) as A 
LEFT JOIN
buy
ON A.name = buy.buyer
ORDER BY net_total DESC;


