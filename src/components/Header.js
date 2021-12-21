import React from "react";

// import { Container, Dropdown, FormControl, Nav, Navbar } from "react-bootstrap";
import {
  Badge,
  Button,
  Container,
  Dropdown,
  FormControl,
  Nav,
  Navbar,
} from "react-bootstrap";
// import {FaShoppingCart} from "react-icons/fa";
import { CartState } from "../context/context";
import { Link, useLocation } from "react-router-dom";
import { FaShoppingCart } from "react-icons/fa";
// import {TiSortAlphabetically} from "react-icons/fa";
import { AiFillDelete } from "react-icons/ai";
// import { Link } from "react-router-dom";
const Header = () => {
  const {
    state: { cart },
    dispatch,
    productDispatch,
  } = CartState();
  return (
    <Navbar bg="primary" variant="dark" style={{ height: 80 }}>
      <Container>
        <Navbar.Brand>
          <a href="./">
            {/* <FaShoppingCart color="white" fontSize="25px" /> */}
            {/* <TiSortAlphabetically/> */}
            Agventure Cart
          </a>
        </Navbar.Brand>
        <Navbar.Text className="search">
          <FormControl
            style={{ width: 500 }}
            placeholder="Search Here...."
            className="m-auto"
            onChange={(e) => {
              productDispatch({
                type: "SORT_BY_SEARCH",
                payload: e.target.value,
              });
            }}
          ></FormControl>
        </Navbar.Text>
        <Nav>
          <Dropdown alignRight>
            <Dropdown.Toggle variant="success">
              <FaShoppingCart color="white" fontSize="25px" />
              <Badge>{cart.length}</Badge>
            </Dropdown.Toggle>
            <Dropdown.Menu style={{ minWidth: 200 }}>
              {cart.length > 0 ? (
                <>
                  {cart.map((prod) => (
                    <span className="cartitem" key={prod.id}>
                      <img
                        src={"https://picsum.photos/400/300"}
                        className="cartItemImg"
                        alt={prod.name}
                      />
                      <div className="cartItemDetail">
                        <span>{prod.name}</span>
                        <span>₹ {prod.price}</span>
                      </div>
                      <AiFillDelete
                        fontSize="20px"
                        style={{ cursor: "pointer" }}
                        onClick={() =>
                          dispatch({
                            type: "REMOVE_FROM_CART",
                            payload: prod,
                          })
                        }
                      />
                    </span>
                  ))}
                  <Link to="/cart">
                    <Button style={{ width: "95%", margin: "0 10px" }}>
                      Go To Cart
                    </Button>
                  </Link>
                </>
              ) : (
                <span style={{ padding: 10 }}>Cart is Empty!</span>
              )}
            </Dropdown.Menu>
          </Dropdown>
        </Nav>
      </Container>
    </Navbar>
  );
};

export default Header;
