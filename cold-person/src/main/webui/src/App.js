import "./App.css";
import Button from "./Button";
import Architecture from "./architecture/Architecture";
import styled from "styled-components"

const Layout = styled.div`
  display: flex;
  flex-direction: row;
`


const Shop = styled.div`
  display: flex;
  flex-direction: column;
  background-color: #282c34;
  color: white;

`

const Title = styled.h1`
  padding: 0.5rem;
`

function App() {
    return (
        <Layout>
            <Shop>
                <Title>
                    Welcome to the sweater shop.
                </Title>
                <Button/>
            </Shop>
            <Architecture/>
        </Layout>
    );
}

export default App;
