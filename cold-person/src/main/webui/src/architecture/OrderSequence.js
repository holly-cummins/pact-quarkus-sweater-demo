import styled from "styled-components"
import Interaction from "./Interaction";

const OrderNumber = styled.div`
  font-weight: bold;
  text-align: center;

`

const OrderSequenceDisplay = styled.div`
  background: transparent;
  align-items: center;
  display: flex;
  flex-direction: row;
  padding: 1rem 1rem;
  margin: 1rem;
  transition: all .5s ease;
  color: #41403E;
  font-size: 1.5rem;
  letter-spacing: 1px;
  outline: none;
  
`


const OrderSequence = ({orderNumber, interactions}) => {
    // We've lost a bit of generic-ness here;
    // ideally we would sort by timestamp instead
    const sortedInteractions = interactions?.filter(interaction => interaction.payload.orderNumber === orderNumber).sort((a, b) => a.timestamp - b.timestamp)

    return (

        <OrderSequenceDisplay>
            <OrderNumber>#{orderNumber}</OrderNumber>
            {sortedInteractions?.map((interaction) => {
                return (
                    <Interaction key={interaction.id} interaction={interaction}/>
                );
            })}
        </OrderSequenceDisplay>
    );
};

export default OrderSequence;
