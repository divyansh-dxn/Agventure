import React from "react";
import { Button, Card } from "react-bootstrap";
import { CartState } from "../context/context";
import Rating from "./Rating";

const Singleproducts = ({ prod }) => {
  const {
    state: { cart },
    dispatch,
    setCart,
  } = CartState();
  return (
    <div className="products">
      <Card>
        <Card.Img
          variant="top"
          src={prod.photoUrl}
          alt={prod.name}
          style={{
            width: 320,
            height: 100,
            objectFit: "cover",
            margin: "auto",
          }}
        />
        <Card.Body>
          <Card.Title>{prod.name}</Card.Title>
          <Card.Subtitle style={{ paddingBottom: 10 }}>
            <span>₹ {prod.price}</span>
            {prod.fastDelivery ? (
              <div>Fast Delivery</div>
            ) : (
              <div>4 days delivery</div>
            )}
            <Rating rating={prod.ratings} />
          </Card.Subtitle>
          {cart.some((p) => p.productId === prod.productId) ? (
            <Button
              onClick={() => {
                // dispatch({
                //   type: "REMOVE_FROM_CART",
                //   payload: prod,
                // });
                setCart(0, cart.find());
                console.log(cart, prod);
              }}
              variant="danger"
            >
              Remove from cart
            </Button>
          ) : (
            <Button
              onClick={() => {
                // dispatch({
                //   type: "ADD_TO_CART",
                //   payload: prod,
                // });
                setCart(1, prod);
              }}
              // disabled={!prod.inStock}
            >
              Add To Cart
              {/* {!prod.inStock ? "Out of stock" : "Add to cart"} */}
            </Button>
          )}
        </Card.Body>
      </Card>
    </div>
  );
};

export default Singleproducts;
