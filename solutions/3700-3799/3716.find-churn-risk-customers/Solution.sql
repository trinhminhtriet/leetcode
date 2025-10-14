# Write your MySQL query statement below
WITH query_cte AS(
    SELECT user_id, event_date, MAX(event_date) OVER(PARTITION BY user_id) max_event_date, event_type, plan_name current_plan, monthly_amount,
    MAX(monthly_amount) OVER(PARTITION BY user_id) max_historical_amount,
    DATEDIFF(MAX(event_date) OVER(PARTITION BY user_id), MIN(event_date) OVER(PARTITION BY user_id)) days_as_subscriber
    FROM subscription_events
)
SELECT DISTINCT user_id , current_plan, monthly_amount current_monthly_amount, max_historical_amount, days_as_subscriber
FROM query_cte q
WHERE event_date = max_event_date AND  
EXISTS (
	SELECT 1 FROM query_cte q1 WHERE q.user_id = q1.user_id AND q1.event_type = 'downgrade'	
)
AND days_as_subscriber > 59 AND monthly_amount / CAST(max_historical_amount AS FLOAT) <= 0.5 AND event_type <> 'cancel'
ORDER BY days_as_subscriber DESC, user_id;