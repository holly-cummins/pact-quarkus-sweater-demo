import {useState} from "react";
import axios from "axios";
import Sweater from "./Sweater";
import styled from "styled-components"

const SweaterOrderer = styled.div`
  padding: 1rem;
  display: flex;
  flex-direction: column;
`

const PushButton = styled.button`
  margin-top: 10px;
`

const Prompt = styled.div`
  margin-bottom: 10px;
`

const Button = () => {
    const [sweaters, setSweaters] = useState([]);
    const [colour, setColour] = useState("");
    const [orderNumber, setOrderNumber] = useState(1);

    const handleSubmit = async () => {
        const newOrderNumber = orderNumber + 1
        setOrderNumber(newOrderNumber)
        try {
            const res = await axios.post(
                "http://localhost:8080/bff/order",
                {
                    colour: colour, orderNumber
                },
                {
                    headers: {
                        "Content-Type": "application/json",
                    },
                }
            );
            setSweaters((sweaters) => [res.data, ...sweaters]);
            setColour("");
        } catch (err) {
            console.error(err.message);
        }
    };

    const handleColourChange = (event) => {
        setColour(event.target.value);
    };

    return (
        <>
            <SweaterOrderer>
                <Prompt>What colour sweater would you like?</Prompt>
                <input
                    type="text"
                    className="form-control"
                    onChange={handleColourChange}
                    value={colour}
                />
                <PushButton type="submit" onClick={handleSubmit}>
                    Order
                </PushButton>
            </SweaterOrderer>
            <div className="sweaters-container">
                {sweaters.map((sweater) => {
                    return (
                        <Sweater key={sweater.orderNumber} sweater={sweater}/>
                    );
                })}
            </div>
        </>
    );
};

export default Button;
