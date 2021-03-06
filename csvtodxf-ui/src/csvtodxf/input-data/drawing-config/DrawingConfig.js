import React, {Component} from 'react'
import {Button, Checkbox, Form, Input, Select} from 'semantic-ui-react'
import {httpModule as http} from "../../shared/httpModule";


class DrawingConfig extends Component {
    constructor(props) {
        super(props);
        this.state = {
            drawingId: this.props.drawingId,
            fileName: this.props.fileName,
            separator: this.separators[0].value,
            textHeight: 1.0,
            doPrintId: true,
            doPrintCoords: false,
            doPrintCode: false,
            doPrintHeight: true,
            doPrint3D: false,
            doLayerByCode: false

        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    isTextHeightValid(textHeightValue) {
        return /^\d+\.\d+$/.test(textHeightValue) || /^\d+$/.test(textHeightValue);
    }
    handleChange(event, data) {
        const value = (data.type === 'checkbox') ? data.checked : data.value;
        this.setState({[data.name]: value});

    }

    toggleLoader() {
        this.props.onToggleLoader();
    }

    submit = (event) => {
        if (this.isTextHeightValid(this.state.textHeight)) {
            this.handleSubmit(event);
        } else {
            this.props.onError({header: "Invalid TextHeight", content: `${this.state.textHeight} is not in valid format, please input a decimal number separated by " . "`})
        }


    };


    handleSubmit(event) {
        event.preventDefault();
        this.props.onError({});
        const requestBody = JSON.stringify(this.state);
        const url = '/convert';
        this.toggleLoader();
        const config = {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        };
        setTimeout( () => {
        http.post(url, requestBody, config).then(response => {
            this.props.onConvertResponse(response.data);
        }).catch(error => {
            this.toggleLoader();
            this.props.onError({header: "Error sending drawing configurations", content: error.message})
        })}, 1000);
    }

    separators = [
        {text: 'comma', value: ','},
        {text: 'semicolon', value: ';'}
    ];

    // Is this unnecessary? the component shows only when previous finished..
    componentWillReceiveProps(nextProps) {
        if (nextProps.fileName !== this.state.fileName) {
            this.setState({fileName: nextProps.fileName});
        }
        if (nextProps.drawingId !== this.state.drawingId) {
            this.setState({drawingId: nextProps.drawingId});
        }
    }

    render() {
        return (
            <div>
                <Form onSubmit={this.submit}>
                    <Form.Group widths="equal">
                        <Form.Field name="separator" control={Select} options={this.separators} label="Separator"
                                    onChange={this.handleChange} value={this.state.separator}/>
                        <Form.Field name="textHeight" control={Input} label="Text Height" value={this.state.textHeight}
                                    onChange={this.handleChange}/>
                    </Form.Group>
                    <Form.Field name="3D" control={Checkbox} label="3D" onChange={this.handleChange}/>
                    <label>What to show on drawing</label>
                    <Form.Group grouped>
                        <Form.Field name="doPrintId" control={Checkbox} onChange={this.handleChange}
                                    label="Point Number" defaultChecked/>
                        <Form.Field name="doPrintHeight" control={Checkbox} onChange={this.handleChange} label="Height"
                                    defaultChecked/>
                        <Form.Field name="doPrintCoords" control={Checkbox} onChange={this.handleChange}
                                    label="Coordinate"/>
                        <Form.Field name="doPrintCode" control={Checkbox} onChange={this.handleChange} label="Code"/>
                        <Form.Field name="doLayerByCode" control={Checkbox} onChange={this.handleChange}
                                    label="Layer By Code"/>


                    </Form.Group>
                    <Form.Field primary control={Button}>Convert</Form.Field>
                </Form>
            </div>
        );

    }
}

export default DrawingConfig;