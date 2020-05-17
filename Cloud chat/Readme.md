The application encrypts the message and then sends the message data to Firebase.
When new messages appear in the database, these messages are downloaded, decrypted, and displayed on the application screen.

To connect Firebase, you must perform the following steps in Android Studio:
Sign in to your Google account
Tools->Firebase
Select Real time Database
Then follow the steps provided by Android Studio.

You need to go to the site https://firebase.google.com/ and go to the console.
Select the created project and go to the Database tab. Configure everything according to the instructions.

The application uses AES encryption in CBC mode. (AES256.kt file)