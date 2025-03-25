-- Firma X jest zależna od firmy Y jeśli firma Y ma udziały w firmie X, lub ma udziały w firmie, która ma udziały w firmie X, itd. Przyjmujemy również, że każda firma jest zależna sama od siebie. (Inaczej, relacja zależności jest domknięciem przechodnio-zwrotnym odwrotności relacji bycia udziałowcem.) Transakcja jest wewnętrzna, jeśli sprzedający i kupujący są zależni od tej samej firmy. Wylicz jaki procent sumarycznej ceny wszystkich transakcji stanowi sumaryczna cena transakcji wewnętrznych. 

WITH RECURSIVE my_boss AS (
  -- startujemy od firm
  SELECT name as company, name as owner
  FROM Company
  
  UNION
  
  -- rozszerzamy do kolejnych wlascicieli
  SELECT my_boss.company as company, Ownership.shareholder AS owner
  FROM my_boss JOIN Ownership 
  ON my_boss.owner = Ownership.company
),
good_transactions AS (
  	SELECT DISTINCT A.id, A.seller, A.buyer, A.price
  	FROM
      (SELECT id, seller, owner as seller_owner, buyer, price
      FROM Sales JOIN my_boss
      ON seller = company) A 
  	JOIN my_boss B
  	ON A.buyer = B.company
  	WHERE A.seller_owner = B.owner
),
good_transactions_sum AS (
  SELECT SUM(price) as good
  FROM good_transactions
),
total_transactions_sum AS (
  SELECT SUM(price) as total
  FROM Sales
)
SELECT 100 * good / total as percentage
FROM total_transactions_sum, good_transactions_sum;
