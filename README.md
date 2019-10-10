# sitter-charge-calculation-service
A service that calculates a babysitter's charge for the night

Install steps

1. `cd sitter-charge-calculation-service/`
2. `mvn clean test spring-boot:run`
3. In your browser, navigate to http://localhost:8080/chargeform



####Calculator description
A babysitter:
- starts no earlier than 5:00PM 
- leaves no later than 4:00AM
- gets paid $12/hour from start-time to bedtime
- gets paid $8/hour from bedtime to midnight
- gets paid $16/hour from midnight to end of job
- gets paid for full hours (no fractional hours)

**Example 1** - 
a babysitter arrives at 7:00PM, 
puts the child to sleep at 1:00AM, and departs at 3:00AM,
he/she would get paid:

12 * 6 + 8 * 0 + 16 * 2 = 72 + 32 = 104

**Explanation** - The first 5 hours are covered under start-time to bedtime rate. Bedtime falls after midnight, so babysitter is still paid the start-time to bedtime rate over this hour. 
Thus, there are 0 hours that encompass the bedtime to midnight rate. 
This brings the total to 72. Finally, the remaining two hours are covered under the midnight to end of job rate, giving a final total of $104.  

**Example 2** -
a babysitter arrives at 8:00PM, 
puts the child to sleep at 8:45:00PM, and departs at 9:30PM,
he/she would get paid:

12 * 0 + 8 * 0 + 16 * 0 = 0 + 0 = 0

**Explanation** - There are no full hours from start-time to bed time or bed time to end of job, so babysitter will receive $0.