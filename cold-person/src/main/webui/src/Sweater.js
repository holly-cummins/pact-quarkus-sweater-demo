import styled from "styled-components"

const OrderNumber = styled.span`
  font-size: 10px;
  color: grey;
`

const Sweater = ({sweater}) => {
    return (
        <div className="sweater-card">
            <p><OrderNumber>#{sweater.orderNumber}</OrderNumber>Your new sweater is
                a {sweater.colour ? `nice ${sweater.colour}` : "totally undescribable"} sweater.</p>
        </div>
    );
};

export default Sweater;
