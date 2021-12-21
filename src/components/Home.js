import React, { useContext } from "react";
import { CartState, Cart } from "../context/context";
import Singleproducts from "./Singleproducts";
import "./styles.css";
import Filters from "./Filters";

const Home = () => {
  const {
    state: { products },
    productState,
  } = useContext(Cart);
  const transformProducts = () => {
    let sortedProducts = products;
    // if (productState.sort) {
    //   sortedProducts = sortedProducts.sort((a, b) =>
    //     productState.sort === "lowToHigh" ? a.pice - b.price : b.price - a.price
    //   );
    // }

    // sortedProducts = sortedProducts.filter((item) => {
    //   if (!productState.byStock) {
    //     if (item.inStock <= 0) {
    //       return false;
    //     }
    //   }
    //   return true;
    // });
    // sortedProducts = sortedProducts.filter((item) => {
    //   if (productState.byFastdelivery) {
    //     return item.fastDelivery;
    //   }
    //   return true;
    // });
    return sortedProducts;
  };

  return (
    <div className="home">
      <Filters />
      <div className="productContainer">
        {transformProducts().map((prod) => {
          return <Singleproducts prod={prod} key={prod.id} />;
        })}
      </div>
    </div>
  );
};

export default Home;
