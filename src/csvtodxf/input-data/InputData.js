import React, {Component} from 'react'
import Fileupload from "./file-upload/Fileupload";
import DrawingConfig from "./drawing-config/DrawingConfig";

class InputData extends Component {
    constructor(props) {
        super(props);
        this.handleFileNameChange = this.handleFileNameChange.bind(this);
        this.handleDrawingIdChange = this.handleDrawingIdChange.bind(this);
        this.handleConvertResponse = this.handleConvertResponse.bind(this);
        this.handleUploadFinished = this.handleUploadFinished.bind(this);

        this.state = {
            drawingId: '',
            fileName: '',
            uploadDone: false,
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

    handleUploadFinished(isDone) {
        console.log("hanlde upload called");
        this.setState({uploadDone: isDone});
    }
    render() {
        let fileName = this.state.fileName;
        let drawingId = this.state.drawingId;
        let isUploadComplete = this.state.uploadDone;
        let drawingConfig;

        if (isUploadComplete) {
            drawingConfig =  <DrawingConfig fileName={fileName} drawingId={drawingId} onConvertResponse={this.handleConvertResponse}/>
        }

        return (
            <div>
                <Fileupload onUploadFinished={this.handleUploadFinished} onFileNameChange={this.handleFileNameChange} onDrawingIdChange={this.handleDrawingIdChange}/>
                {drawingConfig}
            </div>
        );

    }
}

export default InputData;