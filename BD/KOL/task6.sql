WITH RECURSIVE sell AS (
  SELECT seller, SUM(price) as total
  FROM Sales
  GROUP BY seller
),
buy AS (
  SELECT buyer, SUM(price) as total
  FROM Sales
  GROUP BY buyer
),
net_total AS (
  SELECT A.name, COALESCE(A.total, 0) - COALESCE(buy.total, 0) as net_total
  FROM 
      (SELECT * 
      FROM Company 
      LEFT JOIN sell ON Company.name = sell.seller) as A 
  LEFT JOIN
  	buy
  ON A.name = buy.buyer
  ORDER BY net_total DESC
),
ugabuga AS (
  SELECT net_total.name as company, net_total.net_total as cash, 1 as step
  FROM net_total
  
  UNION ALL
  
  SELECT Ownership.shareholder as company, ugabuga.cash * Ownership.fraction as cash, ugabuga.step + 1 as step
  FROM ugabuga JOIN Ownership
  ON ugabuga.company = Ownership.company
),
self_ownership AS (
	SELECT Company.name, 1 - COALESCE(SUM(Ownership.fraction), 0) as psg
  	FROM Company LEFT JOIN Ownership
  	ON Company.name = Ownership.company
  	GROUP BY Company.name
),
total_ugabuga AS (
  SELECT company, SUM(cash) as total 
  FROM ugabuga
  GROUP BY company
)
SELECT company, total * psg as profit
FROM total_ugabuga JOIN self_ownership
ON total_ugabuga.company = self_ownership.name
ORDER BY total * psg DESC;



