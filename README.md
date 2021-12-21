
# Agventure

India is a land of agriculture where variety of crops grows, still the farmers are facing economic problems. Middlemen raise prices for consumers while underpaying farmers, leaving farmers poor and unproductive. Farmers encounter high production cost in their effort to boost production but hardly get fair pricing of their produces from middlemen. Our solution aim at removing middlemen.

## Methodelogy 
We are making an application which would be working as a server transferring messages to firebase.
Dynamically we are generating profile of farmers and interfacing it with frontend where user can order stuff with the payment gateways initially and COD would be available during scaling up of the solution.
For the farmers, we have both gateways, either one can take cash or through any payment gateway. We have taken in consideration that farmers possibly will not have smartphones and can create their account through sms with unique id as their phone numbers (phone numbers are chosen as unique key as nowadays it is connected to one’s aadhar card).
Through app as well as sms farmers can list their products and we also provide them minimum price recommendation to farmers relative to market rates. For delivery, we will be using third party services and the transaction flow is shown in the system architecture.
Technologies involved in this are android , firebase, sms services and React js (for website).

## Solution
The solution for this problem is that if we remove the middlemen from the picture and we make direct contact between the farmer and the consumer. Our solution proposes such an architecture which can act as a interface layer between consumers and farmers.
Our solution consists of following key points:
- Farmers can list their product on our service
- Data will be store on firebase
- The products will be listed on consumer’s app.
- Users will place order through the app and will pay.
- Our system will assign it to the delivery service, and provide the funds to delivery service.
- Delivery service will pick-up the product, provide cash payment to the farmer and deliver it to
   the consumer.
   
## Screenshots
<img alt="Buyer"
        height="423" src=https://github.com/divyansh-dxn/Agventure/blob/main/BUYER.jpeg />
<img alt="Website"
        height="423" src=https://github.com/divyansh-dxn/Agventure/blob/main/Website.jpeg />


### Authors

- [@DivyanshKushwaha](https://github.com/divyansh-dxn)
- [@DeepanshuPratik](https://github.com/DeepanshuPratik)
- [@KshitizKumarSingh](https://github.com/kshitizsingh452)

