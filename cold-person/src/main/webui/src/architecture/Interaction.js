import styled from "styled-components"
import {useState} from "react";

const InteractionDisplay = styled.div`
  align-self: center;
  background: transparent;
  padding: 1rem 1rem;
  margin: 1rem;
  transition: all .5s ease;
  color: #41403E;
  font-size: 1.25rem;
  text-align: center;
  letter-spacing: 1px;
  outline: none;
  box-shadow: 2px 8px 4px -6px hsla(0, 0%, 0%, .3);

  border-top-left-radius: 255px 25px;
  border-top-right-radius: 15px 225px;
  border-bottom-right-radius: 225px 15px;
  border-bottom-left-radius: 15px 255px;

  /*
  Above is shorthand for:
  border-top-left-radius: 255px 15px;
  border-top-right-radius: 15px 225px;
  border-bottom-right-radius: 225px 15px;
  border-bottom-left-radius:15px 255px;
  */

  &:hover {
    box-shadow: 20px 38px 34px -26px hsla(0, 0%, 0%, .2);
  }

  border-width: 4px;
  border-style: solid;
  border-color: ${props => props.isException ? "#993300" : "#41403E"};


`

const Payload = styled.div`
  position: absolute;
  opacity: 0.8;
  background-color: white;

`

const Interaction = ({interaction}) => {

    const [isOpen, setOpen] = useState(false)

    const handleOpen = () => {
        setOpen(true)
    }

    const handleClose = () => {
        setOpen(false)
    }

    // The exception looks like json, but we haven't parsed it, so it's just a string
    const isException = interaction.payload?.includes("exception");
    console.log(isException, "got", interaction.payload, interaction.payload?.exception)

    return (

        <InteractionDisplay onMouseOver={handleOpen}
                            onMouseOut={handleClose} isException={isException}>
            {interaction.methodName}
            {isOpen && (<Payload>
                {interaction.payload}
            </Payload>)}
        </InteractionDisplay>
    );
};

export default Interaction;
