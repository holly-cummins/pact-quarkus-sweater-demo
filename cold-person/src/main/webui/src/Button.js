import { useState } from "react";
import axios from "axios";

const Button = () => {
  const [sweaters, setSweaters] = useState([]);
  const [colour, setColour] = useState("");

  const handleSubmit = async (e) => {
    try {
      const res = await axios.post(
        "http://localhost:8080/sweater/order",
        {
          colour: colour,
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
      <div className="add-sweater-container">
        <input
          type="text"
          className="form-control"
          onChange={handleColourChange}
        />
        <button type="submit" onClick={handleSubmit}>
          Order
        </button>
      </div>
      <div className="sweaters-container">
        {sweaters.map((sweater) => {
          return (
            <div className="sweater-card" key={sweater.colour}>
              <p>Your new sweater is</p>
              <h2 className="sweater-colour">{sweater.colour}</h2>
            </div>
          );
        })}
      </div>
    </>
  );
};

export default Button;
