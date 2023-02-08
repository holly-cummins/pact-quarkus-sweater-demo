import {useEffect, useState} from "react";
import styled from "styled-components"
import OrderSequence from "./OrderSequence";


const InteractionDisplay = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-start;
`

const Interactions = () => {
    const [interactions, setInteractions] = useState([]);

    useEffect(() => {

        const eventSource = new EventSource("http://localhost:8088/recorder/interactionstream");
        eventSource.onmessage = e => {
            const newInteraction = JSON.parse(e.data)

            // The payload will be a json string, so parse that too
            newInteraction.payload = JSON.parse(newInteraction.payload);

            // The endpoint will send us all the data it knows about every time we open a connection,
            // and we open a connection every time we re-render.
            // Do our own duplicate checking, to avoid infinite loops
            // The endpoint also sends us data on a regular cadence and we do not want to re-render then
            if (!interactions.find(interaction => interaction.id === newInteraction.id)) {
                const newInteractions = [...interactions, newInteraction].reverse()
                setInteractions(newInteractions)
            }
        }

        return () => {
            eventSource.close();
        };

    }, [interactions.length]) // eslint-disable-line
    // if we did it eslint's way we would have an infinite loop

    const orderNumbers = [...new Set(interactions.map(interaction => interaction.payload?.orderNumber))].sort().reverse();
    
    return (
        <InteractionDisplay>

            {orderNumbers?.map((orderNumber) => {
                return (
                    <OrderSequence key={orderNumber} orderNumber={orderNumber} interactions={interactions}/>
                );
            })}

        </InteractionDisplay>
    )
};

export default Interactions;
