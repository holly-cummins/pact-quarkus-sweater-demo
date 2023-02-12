import styled from "styled-components"
import OrderSequence from "./OrderSequence";
import {useSSE} from "react-hooks-sse";


const InteractionDisplay = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-start;
`

const Interactions = () => {
    const interactions = useSSE('message', [], {
        stateReducer(prevState, action) {
            const newInteraction = action.data;
            const interactions = prevState || [];
            // The endpoint will send us all the data it knows about every time we open a connection,
            // and we open a connection every time we re-render.
            // Do our own duplicate checking, to avoid infinite loops
            // The endpoint also sends us data on a regular cadence and we do not want to re-render then
            if (!interactions.find(interaction => interaction.id === newInteraction.id)) {
                return [...interactions, newInteraction].reverse()
            } else {
                return interactions;
            }
        }
        ,
        parser(input) {
            const newInteraction = JSON.parse(input)
            // The payload will be a json string, so parse that too
            newInteraction.payload = JSON.parse(newInteraction.payload);
            return newInteraction
        },
    });

    const correlationIds = [...new Set(interactions.map(interaction => interaction.correlationId))].sort().reverse();

    return (
        <InteractionDisplay>

            {correlationIds?.map((correlationId) => {
                const interactionsForThisOrder = interactions?.filter(interaction => interaction.correlationId === correlationId)

                return (
                    <OrderSequence key={correlationId} orderNumber={correlationId}
                                   interactions={interactionsForThisOrder}/>
                );
            })}

        </InteractionDisplay>
    )
};

export default Interactions;
