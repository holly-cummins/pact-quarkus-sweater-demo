import styled from "styled-components"

const OrderNumber = styled.span`
  font-size: 10px;
  color: grey;
`

const Sweater = ({sweater}) => {
    return (
        <div className="sweater-card">
            <p><OrderNumber>#{sweater.orderNumber}</OrderNumber>Your new sweater is a nice {sweater.colour} sweater.</p>
        </div>
    );
};

export default Sweater;
