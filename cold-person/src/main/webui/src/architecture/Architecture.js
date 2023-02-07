import {useEffect, useState} from "react";
import styled from "styled-components"
import Component from "./Component";
import axios from "axios";

const ArchitectureDisplay = styled.div`
  display: flex;
  justify-content: space-around;
  align-items: flex-start;
  background-color: white;
`


const Architecture = () => {
    const [components, setComponents] = useState([]);
    const [interactions, setInteractions] = useState([]);

    useEffect(() => {

        const fetchArchitectureRecords = async () => {
            try {
                const res = await axios.get(
                    "http://localhost:8088/recorder/components",
                    {
                        crossDomain: true,
                        headers: {
                            "Content-Type": "application/json",
                        },
                    }
                );
                setComponents(res?.data);
            } catch (err) {
                console.error(err.message);
            }
        }

        fetchArchitectureRecords()
    }, [components.length]) // eslint-disable-line

    useEffect(() => {

        const eventSource = new EventSource("http://localhost:8088/recorder/componentstream");
        eventSource.onmessage = e => {
            const newComponent = JSON.parse(e.data)

            // The endpoint will send us all the data it knows about every time we open a connection,
            // and we open a connection every time we re-render.
            // Do our own duplicate checking, to avoid infinite loops
            // The endpoint also sends us data on a regular cadence and we do not want to re-render then
            if (!components.find(component => component.id === newComponent.id)) {
                const newComponents = [...components, newComponent]
                setComponents(newComponents)
            }
        }

        return () => {
            eventSource.close();
        };

    }, [components.length]) // eslint-disable-line
// See https://stackoverflow.com/questions/59467758/passing-array-to-useeffect-dependency-list; we want to register the dependency on components, but avoid infinite loops

    useEffect(() => {

        const eventSource = new EventSource("http://localhost:8088/recorder/interactionstream");
        eventSource.onmessage = e => {
            const newInteraction = JSON.parse(e.data)

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

    return (

        <ArchitectureDisplay>

            {components?.map((component) => {
                return (
                    <Component key={component.id} component={component} interactions={interactions}/>
                );
            })}

        </ArchitectureDisplay>
    )
};

export default Architecture;
