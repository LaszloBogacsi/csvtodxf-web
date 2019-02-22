import React, {Component} from 'react'
import {httpModule as http} from "../csvtodxf/shared/httpModule";

export default class Version extends Component {
    constructor(props) {
        super(props);
        this.state = {
            version: ""
        }
    }
    async componentWillMount() {
        let url = process.env.REACT_APP_VERISON_URL;
        const config = {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        };
        const data = await http.get(url, config).then(response => response.data.sha);
        this.setState({
            version: data
        })
    }
    render() {
        return(
            <div className={this.props.className}>Version: {this.state.version}</div>
        )
    }
}