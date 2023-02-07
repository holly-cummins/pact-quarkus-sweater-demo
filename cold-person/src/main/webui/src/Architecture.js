import {useEffect, useState} from "react";
import Component from "./Component";
import axios from "axios";
import styled from "styled-components"

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

        fetchArchitectureRecords();
    }, [])


    return (
        <>
            <ArchitectureDisplay>
                {components?.map((component) => {
                    return (
                        <Component key={component.name} component={component}/>
                    );
                })}
            </ArchitectureDisplay>
        </>
    );
};

export default Architecture;
