# README

## About this project
This is a basic rule based random generator. Essentially, it generates random values from within a given
domain, enforcing a simple rule:

>*If a same value is chosen randomly during a specific amount of times, then it will get penalized by being temporarily
separated from the domain. Both the amount of turns of the penalty and the allowed number of repetitions are defined by
the user.*

Although the values are defined by the user, they must follow a specific criteria.

## Explaining the criteria

In order to avoid a scenario in which we want to generate a random value and there are no values left within the domain
due to penalties being enforced, the following condition is born: 

>**(Length of domain) > (Penalty time + Max. Allowed Repetitions - 1) \ (Max. Allowed Repetitions)**

If you are wondering why? or how did we get to this formula/condition? I will explain it now.

First, since we are dealing with randomness, it would be hard to know when this application might end up without values
to choose from. So, we put ourselves in the worst case scenario of randomness for our application: **we only get repeated
values**. This means that we will get repeated values until penalty is enforced and a different value has to be chosen,
but then it will be chosen repeatedly also, and so on.

Then, to simplify even more the task of finding a point in which the application might fail, we must discover the turn
when the application will have as many domain values being separated due to penalties, and this situation can be reached
more than once so we will work with the first time this happen.

From now on "Maximum repetitions allowed" will be referred as M, and "Penalty time" as P.

Now, going back to the question of when will we have as many values being penalized for the first time, considering what
we have already stated in the first paragraph as the worst case scenario of only having repeated values, we could say
that after M turns we will have our first penalized values and after P turns more, this first penalized value would be 
reincorporated to the domain, making the turn in which we would have as many as possible values being separated from the
domain the (P+M-1)th turn.

Having already identified the turn at which the application might fail for first time if the parameters were badly
assigned, we now will proceed to see how many values would have been penalized until that point in time. For that, we
will only do:

>(P+M-1) \ M 

This is the entire division of the turns by the amount of turns it takes to receive a penalty, it will result in how
many values are penalized in total.

Finally, in order to make sure the application will not run out of values within the domain we will enforce the rule of
the domain being larger than the maximum amount of penalized values that will eventually exist given the P and M
values provided by the user.