import {useState} from "react";
import axios from "axios";
import Sweater from "./Sweater";
import styled from "styled-components"

const SweaterOrderer = styled.div`
  padding: 1rem;
`

const Button = () => {
    const [sweaters, setSweaters] = useState([]);
    const [colour, setColour] = useState("");
    const [orderNumber, setOrderNumber] = useState(0);

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
                <input
                    type="text"
                    className="form-control"
                    onChange={handleColourChange}
                />
                <button type="submit" onClick={handleSubmit}>
                    Order
                </button>
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
