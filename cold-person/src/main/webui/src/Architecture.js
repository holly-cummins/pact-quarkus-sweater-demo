import {useEffect, useState} from "react";
import styled from "styled-components"
import Component from "./Component";
import axios from "axios";

const ArchitectureDisplay = styled.div`
  display: flex;
  background-color: white;
`


const Architecture = () => {
    const [components, setComponents] = useState([]);

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
    }, [components.length])

    useEffect(() => {

        const eventSource = new EventSource("http://localhost:8088/recorder/componentstream");
        eventSource.onmessage = e => {
            const newComponent = JSON.parse(e.data)
            newComponent.name = newComponent.name

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

    }, [components.length])
// See https://stackoverflow.com/questions/59467758/passing-array-to-useeffect-dependency-list; we want to register the dependency on components, but avoid infinite loops

    return (

        <ArchitectureDisplay>
            {components?.map((component) => {
                return (
                    <Component key={component.id} component={component}/>
                );
            })}
        </ArchitectureDisplay>
    )
};

export default Architecture;
