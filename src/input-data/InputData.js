import React, {Component} from 'react'
import Fileupload from "../file-upload/Fileupload";
import DrawingConfig from "../drawing-config/DrawingConfig";

class InputData extends Component {
    render() {
        return (
            <div>
                <Fileupload/>
                <DrawingConfig/>
            </div>
        );

    }
}

export default InputData;