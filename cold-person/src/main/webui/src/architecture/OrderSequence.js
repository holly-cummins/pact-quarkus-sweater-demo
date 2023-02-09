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
  outline: none;

`


const OrderSequence = ({orderNumber, interactions}) => {

    const interactionsWithCollapsedComponents = interactions.reduce((acc, interaction) => {
        let pair = acc.find(el => el.component === interaction.owningComponent)
        if (!pair) {
            pair = {component: interaction.owningComponent}
            acc.push(pair);
        }
        pair[interaction.type?.toLowerCase()] = interaction;

        return acc;
    }, [])

    const sortedInteractions = interactionsWithCollapsedComponents.sort((a, b) => a.request?.timestamp - b.request?.timestamp)

    return (

        <OrderSequenceDisplay>
            <OrderNumber>#{orderNumber ? orderNumber : "??"}</OrderNumber>
            {sortedInteractions?.map((pair) => {
                return (
                    <Interaction key={pair.request?.id || pair.response?.id} request={pair.request}
                                 response={pair.response}/>
                );
            })}
        </OrderSequenceDisplay>
    );
};

export default OrderSequence;
