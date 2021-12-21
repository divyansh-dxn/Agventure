// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getFirestore, collection, getDocs } from "firebase/firestore/lite";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyCrjQSCWmHwhqXzm9rcoMWieJVvYhOT0GM",
  authDomain: "dxn-agventure.firebaseapp.com",
  projectId: "dxn-agventure",
  storageBucket: "dxn-agventure.appspot.com",
  messagingSenderId: "644616772073",
  appId: "1:644616772073:web:e2815a870874e0732cd378",
  measurementId: "G-4D8JJ9QGDV",
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const db = getFirestore(app);

export const getProducts = async () => {
  const productsCol = collection(db, "products_collection");
  const productsSnapshot = await getDocs(productsCol);
  const productsList = productsSnapshot.docs.map((item) => item.data());
  //   console.log(productsList);
  return productsList;
};

export const getUsers = async () => {
  const usersCol = collection(db, "users_collection");
  const usersSnapshot = await getDocs(usersCol);
  const userList = usersSnapshot.docs.map((item) => item.data());
  return userList;
};
