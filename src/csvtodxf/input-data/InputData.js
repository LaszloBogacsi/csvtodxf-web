import React, {Component} from 'react'
import Fileupload from "./file-upload/Fileupload";
import DrawingConfig from "./drawing-config/DrawingConfig";

class InputData extends Component {
    constructor(props) {
        super(props);
        this.handleFileNameChange = this.handleFileNameChange.bind(this);
        this.handleDrawingIdChange = this.handleDrawingIdChange.bind(this);
        this.handleConvertResponse = this.handleConvertResponse.bind(this);

        this.state = {
            drawingId: '',
            fileName: ''
        }
    }

    handleFileNameChange(fileName) {
        this.setState({fileName: fileName})
    }

    handleDrawingIdChange(drawingId) {
        this.setState({drawingId: drawingId})
    }

    handleConvertResponse(convertResponse) {
        this.props.onConvertResponse(convertResponse);
    }
    render() {
        let fileName = this.state.fileName;
        let drawingId = this.state.drawingId;
        return (
            <div>
                <Fileupload onFileNameChange={this.handleFileNameChange} onDrawingIdChange={this.handleDrawingIdChange}/>
                <DrawingConfig fileName={fileName} drawingId={drawingId} onConvertResponse={this.handleConvertResponse}/>
            </div>
        );

    }
}

export default InputData;