import styled from "styled-components"
import Interactions from "./Interactions";
import Components from "./Components";

const ArchitectureDisplay = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-start;
  background-color: white;
`

const Architecture = () => {
    return (

        <ArchitectureDisplay>
            <Components/>

            <Interactions/>
        </ArchitectureDisplay>
    )
};

export default Architecture;
