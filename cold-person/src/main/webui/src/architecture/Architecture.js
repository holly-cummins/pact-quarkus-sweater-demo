import styled from "styled-components"
import Interactions from "./Interactions";

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
            {/*<Components/> Do not draw the components until we have extracted common code for the rough.js style*/}

            <Interactions/>
        </ArchitectureDisplay>
    )
};

export default Architecture;
