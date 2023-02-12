import styled from "styled-components"
import Interaction from "./Interaction";
import {useMemo} from "react";

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

const stripNulls = (obj) => {
    return Object.entries(obj).reduce((a, [k, v]) => (v ? (a[k] = v, a) : a), {})
}

const sortInteractions = (interactions) => {
    const interactionsWithCollapsedComponents = interactions.reduce((acc, interaction) => {
        let pair = acc.find(el => el.component === interaction.owningComponent)
        if (!pair) {
            pair = {component: interaction.owningComponent}
            acc.push(pair);
        }
        // Don't overwrite existing interactions of this type for this component
        const interactionType = interaction.type?.toLowerCase()
        const existingInteraction = pair[interactionType];
        if (!existingInteraction) {
            pair[interactionType] = interaction;
        } else {
            // Instead, merge them
            // Do a shallow merge rather than trying to do elaborate payload merges
            // Do strip out keys where there isn't a value, though, or null can overwrite a populated value
            pair[interactionType] = {...stripNulls(existingInteraction), ...stripNulls(interaction)};
        }

        return acc;
    }, [])

    // Sort by request time, not response time
    return interactionsWithCollapsedComponents.sort((a, b) => a.request?.timestamp - b.request?.timestamp)
}

const OrderSequence = ({orderNumber, interactions}) => {

    const sortedInteractions = useMemo(() => sortInteractions(interactions), [interactions.length])

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
