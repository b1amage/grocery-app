# Android Grocery Application

### Course Information
- University: Royal Melbourne Institution of Technology (RMIT)
- Campus: Saigon South (SGS), Vietnam


- Course: Android Development
- Course code: COSC2657
- Lecturer: Mr. Minh Vu Thanh


- Assignment number: 3
- Assignment title: Group Assignment
- Assignment type: Group Assignment

### Contributors
- Supervisor: Mr. Minh Vu Thanh
- Contributor: Nguyen Luu Quoc Bao (s3877698) -> 25%
- Contributor: Dao Kha Tuan (s3877347) -> 25%
- Contributor: Bui Quang An (s3877482) -> 25%
- Contributor: Trieu Hoang Khang (s3878466) -> 25%

### Project Information
- Project name: GRMIT Application
- Purpose: Shopping
- Functionalities: 
    # Staff 

        Staff can CRUD items (CMS Dashboard) 

        Staff can add a new location of a store, which will be displayed on map, and search them by address 

        Staff can add new discount vouchers for users, search them by voucher’s code 

        Staff can view orders and complete the order after the order is fulfilled 

    # User 

        * Authentication module 

        User can register (username, phone, email, password, confirm password, address). After filling information, server will send an OTP to email of user, and user enters the OTP to finish login. 

        User can login (email, password). When logged in, cookies will be set on user’s device 

        User can reset password in case forget by entering the email of that account, then users enter the new password and confirm it again, then server will send an OTP to the email. After entering the OTP, the account will be reset with new password. 

        * Item module 

        User can view a list of items, only price, image, name, category 

        User can filter items by name 

        User can filter items by category (vegetables, meat, fruit, diary, canned, snack, drinks, spice, household, personal hygiene) 

        User can view item’s details, including price, image, name, category, description 

        User can add item to cart, which include information such as price, image, name, category and quantity. 

        User can adjust items quantity or remove item from cart to place a new order to staff 

        * Payment module 

        User can place order (from cart which is stored in SQLite or SharePreference) 

        User can accumulate point (1000d = 2 points), everytime user makes an order larger than 100.000d, user will gain 1 point. 

        User can use accumulated point for discount, or voucher can also be used to apply for discount too, or both of them can be used at the same time for discount 

        The voucher includes: title, description, type of discount (points or percent), and value of the voucher 

        User has Cash on Delivery (COD) payment 

     # Others 

        User can view stores on Google map 

        Feedback: user can send feedbacks to staff,  and staff can view feedbacks from users 

        JWT token will be expired after 30 days with background service. 

        Check Internet connection with Broadcast Receivers. 
        
- Technologies used: Java (jdk16), SQLite, Picasso, MongoDB, NodeJS, ExpressJS, Restful API, Volley, Google Map, Json Web Token, Shimmer, Service, Broadcase Receiver


- Project started date: 8 December 2022
- Project finished date: 20 January 2023
    
### Known bug
No known bug found

### To Run
- Run the SplashActivity

### Note
- Demonstration: 
- Simulator built in demonstration: Pixel 3a

### Supporting tools used
- Android Studio: for configuration and coding the app

### References
