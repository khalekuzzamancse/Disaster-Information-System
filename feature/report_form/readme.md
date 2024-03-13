
## About this module

- Define the Report Form and the form components
- Define the form controller but does not manage it
- It able to  change the report form style,design and other without affecting client.

- Since there's are not many ui or screen to this module, only single screen so it manages the adaptive ui as well as the send data to the servers
- It has adaptive layout for compact and larger screens
- It managed the permission that needed for it such as location permissions,and permission are platform dependent so use the expect-actual 


## Public APIs
- Destination
- FormController
- 


### Client responsibility in order to use it
- Since `FORM` data should preserve util the app death that is why the client need to persevere it state 


### Implementation Guide


- Make some variables and functions internal even though the class will be used by client

- Use `Factory Design pattern` for creating controlled or sending to network so that it loose  coupled because we want dependency inversion if we do so then the client need to pass the dependency in that case the client have more and unnecessary responsibility.to reduce that use factory so that implementation can be changed or replaced from only single place

