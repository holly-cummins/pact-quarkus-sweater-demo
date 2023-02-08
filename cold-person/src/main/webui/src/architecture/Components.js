import {useEffect, useState} from "react";
import styled from "styled-components"
import Component from "./Component";
import axios from "axios";

const ArchitectureDisplay = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-start;
  background-color: white;
`

const ComponentDisplay = styled.div`
  display: flex;
  justify-content: space-around;
  align-items: flex-start;
`

const Components = () => {
    const [components, setComponents] = useState([]);

    // Get a starting list of components, and subscribe to the SSE stream. This does seem to result in a bit of
    // double-rendering, so we should add a guard and only do this non-stream fetch if the SSE stream isn't active
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

    return (
        <ComponentDisplay>
            {components?.map((component) => {
                return (
                    <Component key={component.id} component={component}/>
                );
            })}
        </ComponentDisplay>

    )
};

export default Components;
