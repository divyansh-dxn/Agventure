import React, {
  createContext,
  useReducer,
  useContext,
  useState,
  useEffect,
} from "react";
// import {createContext} from "react"
import faker from "faker";
import { cartReducer } from "./Reducers";
import { getProducts } from "../firebase";

import { productReducer } from "./Reducers";
import { async } from "@firebase/util";
export const Cart = createContext();
faker.seed(99);

const Context = ({ children }) => {
  faker.seed(100);
  const [state, setState] = useState({
    products: [],
    cart: [],
  });
  const setCart = (type, item) => {
    if (type) {
      setState({ ...state, cart: [...state.cart, { item, quantity: 1 }] });
    } else {
      setState({ ...state, cart: [...state.cart, { item, quantity: 0 }] });
    }
  };
  useEffect(async () => {
    // const intervalID = setInterval(async () => {
    const tempProducts = await getProducts();
    setState({ cart: state.cart, products: tempProducts });
    // console.log(tempProducts);
    // });
    // return () => {
    // clearInterval(intervalID);
    // };
  }, []);
  // const products = [...Array(20)].map(() => ({
  //   id: faker.datatype.uuid(),
  //   name: faker.commerce.productName(),
  //   price: faker.commerce.price(),
  //   image: faker.random.image(),
  //   inStock: faker.random.arrayElement([0, 3, 5, 6, 7]),
  //   fastDelivery: faker.datatype.boolean(),
  //   ratings: faker.random.arrayElement([1, 2, 3, 4, 5]),
  // }));

  // const [state, dispatch] = useReducer(cartReducer, {
  //   products: products,
  //   cart: [],
  // });

  const [productState, productDispatch] = useReducer(productReducer, {
    byStock: false,
    byFastDelivery: false,
    byRating: 0,
    searchQuery: "",
  });

  return (
    <Cart.Provider
      value={{ state, /*dispatch,*/ productState, productDispatch, setCart }}
    >
      {children}
    </Cart.Provider>
  );
};

export default Context;
export const CartState = () => {
  return useContext(Cart);
};
