WITH ordered_data AS (
    SELECT buyer, id, price, LEAD(price) OVER (PARTITION BY buyer ORDER BY id) AS next_price
    FROM Sales
),
good_sales AS (
	SELECT buyer, id
 	FROM ordered_data
 	WHERE price >= 2 * next_price
)
  
SELECT name, COALESCE(COUNT(good_sales.id), 0) as cnt
FROM Company LEFT JOIN good_sales
ON Company.name = good_sales.buyer
GROUP BY name
ORDER BY COALESCE(COUNT(good_sales.id), 0) DESC;
